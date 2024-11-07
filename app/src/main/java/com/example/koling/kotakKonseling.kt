package com.example.koling

import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.koling.databinding.ActivityKotakKonselingBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class kotakKonseling : AppCompatActivity() {

    private lateinit var binding: ActivityKotakKonselingBinding
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        val database = FirebaseDatabase.getInstance()
        myRef = database.getReference("kotak-konseling")


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKotakKonselingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            gotoMain()
        }
        binding.btnKirim.setOnClickListener{
            val pesan = binding.etKeluhan.text.toString()
            val timestamp = tanggal()
            val email = getemailFromPrefs() ?: "Unknown"
            val username = getUsernameFromPrefs() ?: "Unknown" // Mengambil nama pengguna atau default "Unknown" jika kosong

            data class ChatMessage(
                val email: String = "UserPrefs",
                val username: String = "UserPrefs", // Tambahkan field username
                val pesan: String = "",
                val timestamp: String = ""
            )

            val message = ChatMessage(email, username, pesan, timestamp)

            if (pesan.isNotEmpty()) {
                myRef.push().setValue(message)
                binding.etKeluhan.text.clear()
                Berhasil()
            }
        }
    }

    private fun getUsernameFromPrefs(): String? {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("username", null)
    }

    private fun getemailFromPrefs(): String? {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("email", null)
    }


    private fun tanggal(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun gotoMain(){
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun Berhasil(){
        Intent(this, berhasilKirim::class.java).also {
            startActivity(it)
            finish()
        }
    }
}