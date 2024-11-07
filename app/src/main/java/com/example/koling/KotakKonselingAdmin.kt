package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koling.databinding.ActivityKotakKonselingAdminBinding
import com.example.koling.databinding.ActivityKotakKonselingBinding

class KotakKonselingAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityKotakKonselingAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotakKonselingAdminBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        /// Ambil data dari Intent
        val username = intent.getStringExtra("username")
        val pesan = intent.getStringExtra("pesan")
        val timestamp = intent.getStringExtra("timestamp")

        // Tampilkan data ke UI
        binding.tvUsername.text = username
        binding.tvWaktu.text = timestamp
        binding.etKeluhan.setText(pesan) // Menampilkan pesan ke EditText

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }

        binding.btnTerima.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

//    private fun gotoMain(){
//        Intent(this, MainAdmin::class.java).also {
//            startActivity(it)
//            finish()
//        }
//    }
}