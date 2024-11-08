package com.example.koling

import android.content.Intent
import android.os.Bundle
import com.example.koling.Message

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.koling.adapter.ChatAdapter
import com.example.koling.adapter.InChatAdapter
import com.example.koling.databinding.ActivityInChatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.auth.FirebaseAuth

class InChat : AppCompatActivity() {

    private lateinit var binding: ActivityInChatBinding
    private lateinit var chatDatabaseRef: DatabaseReference
    private lateinit var InchatAdapter: InChatAdapter // Adapter untuk menampilkan pesan di RecyclerView
    private val messagesList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val namaBK = intent.getStringExtra("namaBK")
        val fotoUrl = intent.getStringExtra("fotoUrl")

        // Set data ke tampilan
        binding.namaBK1.text = namaBK
        Glide.with(this).load(fotoUrl).into(binding.foto1)

        // Kembali ke halaman utama
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Chatting timbal balik
        setupChat()
    }
    private fun setupChat() {
        // Inisialisasi RecyclerView untuk chat
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(this)
        InchatAdapter = InChatAdapter(messagesList)
        binding.recyclerViewChat.adapter = InchatAdapter

        // Inisialisasi referensi database chat
        val chatId = intent.getStringExtra("chatId") ?: UUID.randomUUID().toString()
        chatDatabaseRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId)

        // Muat pesan dan dengarkan pesan baru
        loadMessages()

        // Tombol kirim pesan
        binding.btnSend.setOnClickListener {
            val messageText = binding.etChat.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
                binding.etChat.text.clear()
            }
        }
    }

    private fun loadMessages() {
        chatDatabaseRef.child("messages").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messagesList.clear()
                for (dataSnapshot in snapshot.children) {
                    val message = dataSnapshot.getValue(Message::class.java)
                    if (message != null) {
                        messagesList.add(message)
                    }
                }
                InchatAdapter.notifyDataSetChanged()
                binding.recyclerViewChat.scrollToPosition(messagesList.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                // Tangani error
            }
        })
    }

    private fun sendMessage(messageText: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val messageId = chatDatabaseRef.child("messages").push().key ?: return
        val timestamp = System.currentTimeMillis()
        val message = Message(
            senderId = currentUser?.uid,
            sender = currentUser?.displayName ?: "User",
            text = messageText,
            timestamp = timestamp
        )
        chatDatabaseRef.child("messages").child(messageId).setValue(message)
    }
}