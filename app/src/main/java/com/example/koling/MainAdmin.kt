package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.koling.FragmentAdmin.HomeChatFragment
import com.example.koling.FragmentAdmin.HomeKolingFragment
import com.example.koling.databinding.ActivityMainAdminBinding

class MainAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityMainAdminBinding
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goToFrag(HomeKolingFragment())

        binding.akun.setOnClickListener{
            gotoAkun()
        }

        binding.btnChat.setOnClickListener{
            goToFrag(HomeChatFragment())
        }
        binding.btnKotak.setOnClickListener{
            goToFrag(HomeKolingFragment())
        }
    }

    private fun goToFrag(fragment: Fragment){
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.Fragment_Container, fragment).commit()
    }

    private fun gotoAkun(){
        Intent(this, AkunAdmin::class.java).also {
            startActivity(it)
            finish()
        }
    }
}