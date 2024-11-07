package com.example.koling.FragmentAdmin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koling.InChatAdmin
import com.example.koling.MainAdmin
import com.example.koling.R
import com.example.koling.databinding.FragmentHomeChatBinding


class HomeChatFragment : Fragment() {

    private lateinit var binding: FragmentHomeChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeChatBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.message.setOnClickListener{
            val intent = Intent(requireContext(), InChatAdmin::class.java)
            startActivity(intent)
        }
    }

}