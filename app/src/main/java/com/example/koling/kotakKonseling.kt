package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koling.databinding.ActivityKotakKonselingBinding

class kotakKonseling : AppCompatActivity() {

    private lateinit var binding: ActivityKotakKonselingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityKotakKonselingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            gotoMain()
        }
        binding.btnKirim.setOnClickListener{
            Berhasil()
        }
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