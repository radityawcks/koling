package com.example.koling

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.koling.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private val handler = Handler(Looper.getMainLooper())
    private var isForward = true // Variabel untuk melacak arah animasi

    // Daftar fragment untuk ditampilkan bergantian
    private val fragments = listOf(
        KonselingFragment(),
        CurhatFragment()
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager = supportFragmentManager
        showFragment(fragments[currentIndex])

        binding.akun.setOnClickListener{
            gotoAkun()
        }

        // Jadwalkan perpindahan fragment secara otomatis
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentIndex = (currentIndex + 1) % fragments.size
                showFragment(fragments[currentIndex])
                handler.postDelayed(this, 5000)
            }
        }, 2000)
    }

    private fun gotoAkun(){
        Intent(this, AkunUser::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun showFragment(fragment: Fragment) {
        // Cek arah animasi berdasarkan `isForward`
        val enterTranslation = if (isForward) -1000f else 1000f
        val exitTranslation = if (isForward) 1000f else -1000f

        // Fragment lama (jika ada) akan digeser keluar
        val currentFragment = fragmentManager.findFragmentById(R.id.Fragment_Container)
        currentFragment?.view?.let { view ->
            ObjectAnimator.ofFloat(view, "translationX", 0f, exitTranslation).apply {
                duration = 300
                start()
            }
        }

        // Fragment baru akan digeser masuk
        fragmentManager.beginTransaction().replace(R.id.Fragment_Container, fragment).commitNow()
        fragment.view?.let { view ->
            view.translationX = enterTranslation
            ObjectAnimator.ofFloat(view, "translationX", enterTranslation, 0f).apply {
                duration = 300
                start()
            }
        }

        // Balik arah animasi untuk transisi berikutnya
        isForward = !isForward
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Hentikan handler saat Activity dihancurkan
    }
}