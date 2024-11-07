package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koling.databinding.ActivityInChatBinding

class InChat : AppCompatActivity() {

    private lateinit var binding: ActivityInChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
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