package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koling.databinding.FragmentCurhatBinding


class CurhatFragment : Fragment() {
    private lateinit var binding: FragmentCurhatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurhatBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnCurhat.setOnClickListener{
            gotoMain()
        }
        return view
    }
    private fun gotoMain() {
        Intent(requireContext(), Curhat::class.java).also {
            startActivity(it)
            requireActivity().finish()
        }
    }

}