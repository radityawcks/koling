package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koling.databinding.ActivityInChatAdminBinding

class InChatAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityInChatAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInChatAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}