package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koling.databinding.ActivityBerhasilKirimBinding

class berhasilKirim : AppCompatActivity() {

    private lateinit var binding: ActivityBerhasilKirimBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBerhasilKirimBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKembali.setOnClickListener{
            gotoMain()
        }
    }
    private fun gotoMain(){
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}