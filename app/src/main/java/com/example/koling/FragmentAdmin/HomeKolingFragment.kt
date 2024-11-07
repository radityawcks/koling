package com.example.koling.FragmentAdmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koling.DataChat
import com.example.koling.InChatAdmin
import com.example.koling.KotakKonselingAdmin
import com.example.koling.R
import com.example.koling.databinding.FragmentHomeKolingBinding
import com.example.koling.databinding.FragmentKonselingBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koling.adapter.ChatAdapter
import java.util.Date
import java.util.Locale

class HomeKolingFragment : Fragment() {

    private lateinit var binding: FragmentHomeKolingBinding
    private lateinit var database: DatabaseReference
    private lateinit var chatList: MutableList<DataChat>
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeKolingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatList = mutableListOf()
        adapter = ChatAdapter(chatList)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        adapter.onItemClick = { dataChat ->
            val intent = Intent(requireContext(), KotakKonselingAdmin::class.java)
            intent.putExtra("username", dataChat.username)
            intent.putExtra("timestamp", dataChat.timestamp)
            intent.putExtra("pesan", dataChat.pesan)  // Pass pesan ke activity
            startActivity(intent)
        }

        // Ambil data dari Firebase
        database = FirebaseDatabase.getInstance().getReference("kotak-konseling")

        database.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    chatList.clear()  // Clear data sebelumnya
                    for (chatSnapshot in snapshot.children) {
                        val dataChat = chatSnapshot.getValue(DataChat::class.java)
                        dataChat?.let {
                            // Pastikan data sesuai dengan hari ini
                            if (it.timestamp.startsWith(getCurrentDate())) {
                                chatList.add(it)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged() // Update RecyclerView
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase Error", error.message)
            }
        })
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }


}