package com.example.koling.FragmentAdmin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koling.InChatAdmin
import com.example.koling.KotakKonselingAdmin
import com.example.koling.R
import com.example.koling.databinding.FragmentHomeKolingBinding
import com.example.koling.databinding.FragmentKonselingBinding

class HomeKolingFragment : Fragment() {

    private lateinit var binding:FragmentHomeKolingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeKolingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.keluhan.setOnClickListener{
            val intent = Intent(requireContext(), KotakKonselingAdmin::class.java)
            startActivity(intent)
        }
    }


}