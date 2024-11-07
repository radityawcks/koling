package com.example.koling

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koling.databinding.ActivityKotakKonselingBinding
import com.example.koling.databinding.FragmentCurhatBinding
import com.example.koling.databinding.FragmentKonselingBinding


class KonselingFragment : Fragment() {

    private lateinit var binding: FragmentKonselingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKonselingBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btncerita.setOnClickListener{
            gotoMain()
        }
        return view
    }
    private fun gotoMain() {
        Intent(requireContext(), kotakKonseling::class.java).also {
            startActivity(it)
            requireActivity().finish()
        }
    }

}