package com.example.koling

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koling.databinding.ActivityAkunUserBinding
import com.google.firebase.firestore.FirebaseFirestore

class AkunUser : AppCompatActivity() {

    private lateinit var binding: ActivityAkunUserBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAkunUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null) // Pastikan kunci ini sama dengan yang digunakan saat login
        val username = sharedPreferences.getString("username", null)

        binding.tvUsername.text = username
        binding.tvnama.text = username // Ganti sesuai kebutuhan

        // Ambil data username dari Firestore
        loadUserData()

        binding.btnBack.setOnClickListener {
            gotoMain()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null) // Pastikan kunci ini sama dengan yang digunakan saat login
        val username = sharedPreferences.getString("username", null)
        Log.d("AkunUser", "Email dari SharedPreferences: $email") // Logging email

        if (email != null) {
            // Ambil data pengguna dari Firestore berdasarkan email
            db.collection("users").document(email).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
//                        val username = document.getString("username") ?: "Username tidak ditemukan"

                        // Set nilai username dan fullname ke TextView
                        binding.tvUsername.text = username
                        binding.tvnama.text = username // Ganti sesuai kebutuhan
                    } else {
                        Log.d("Firestore", "Document not found")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Firestore", "Error fetching user data", exception)
                }
        } else {
            Log.e("AkunUser", "Email not found in SharedPreferences")
//            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Hapus semua data
        editor.apply()

        // Kembali ke halaman login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun gotoMain() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}