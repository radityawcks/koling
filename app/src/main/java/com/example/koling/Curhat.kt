package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koling.databinding.ActivityCurhatBinding

class Curhat : AppCompatActivity() {

    private lateinit var binding: ActivityCurhatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCurhatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            gotoMain()
        }

        binding.pakGigs.setOnClickListener{
            gotoChat()
        }

    }
    private fun gotoMain(){
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
    private fun gotoChat(){
        Intent(this, InChat::class.java).also {
            startActivity(it)
            finish()
        }
    }
}