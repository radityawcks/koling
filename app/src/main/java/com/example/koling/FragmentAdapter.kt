package com.example.koling

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity) {
        // Daftar fragment yang akan ditampilkan dalam ViewPager2
        private val fragments = listOf(
            KonselingFragment(),
            CurhatFragment()
        )

        // Jumlah item dalam adapter
        override fun getItemCount(): Int = fragments.size

        // Membuat fragment berdasarkan posisi
        override fun createFragment(position: Int): Fragment = fragments[position]
}