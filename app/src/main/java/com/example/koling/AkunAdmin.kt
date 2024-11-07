package com.example.koling

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koling.databinding.ActivityAkunAdminBinding
import com.google.firebase.firestore.FirebaseFirestore

class AkunAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityAkunAdminBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAkunAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null) // Pastikan kunci ini sama dengan yang digunakan saat login
        val username = sharedPreferences.getString("username", null)
        val jabatan = sharedPreferences.getString("jabatan", null)
        val photoUrl = sharedPreferences.getString("foto", null)

        binding.tvUsername.text = username
        binding.tvJabatan.text = jabatan
        binding.tvemail1.text = email
        binding.tvnama.text = username // Ganti sesuai kebutuhan

        if (photoUrl != null) {
            Glide.with(this)
                .load(photoUrl)
                .into(binding.foto1) // Ganti dengan ID ImageView yang sesuai
        }


        loadAdminData()

        binding.btnLogout.setOnClickListener{
            logout()
        }

        binding.btnBack.setOnClickListener{
            gotoMain()
        }

    }

    private fun loadAdminData() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null) // Pastikan kunci ini sama dengan yang digunakan saat login
        val username = sharedPreferences.getString("username", null)
        val jabatan = sharedPreferences.getString("jabatan", null)
        val photoUrl = sharedPreferences.getString("foto", null)
        Log.d("AkunUser", "Email dari SharedPreferences: $email") // Logging email

        if (email != null) {
            // Ambil data pengguna dari Firestore berdasarkan email
            db.collection("admins").document(email).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
//                        val username = document.getString("username") ?: "Username tidak ditemukan"

                        // Set nilai username dan fullname ke TextView
                        if (photoUrl != null) {
                            Glide.with(this)
                                .load(photoUrl)
                                .into(binding.foto1) // Ganti dengan ID ImageView yang sesuai
                        }
                        binding.tvJabatan.text = jabatan
                        binding.tvUsername.text = username
                        binding.tvemail1.text = email
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
    private fun gotoMain(){
        Intent(this, MainAdmin::class.java).also {
            startActivity(it)
            finish()
        }
    }
}