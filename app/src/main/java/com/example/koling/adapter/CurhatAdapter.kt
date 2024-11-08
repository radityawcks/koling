package com.example.koling.adapter

import com.example.koling.Curhat
import com.example.koling.databinding.ItemCurhatBinding

// AdminAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koling.model.Admin
import com.bumptech.glide.Glide
import com.google.firebase.database.collection.R

class CurhatAdapter(private val adminList: List<Admin>) : RecyclerView.Adapter<CurhatAdapter.CurhatViewHolder>() {

    class CurhatViewHolder(val binding: ItemCurhatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurhatViewHolder {
        val binding = ItemCurhatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurhatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurhatViewHolder, position: Int) {
        val admin = adminList[position]
        holder.binding.namaBK1.text = admin.username

        // Menggunakan Glide untuk menampilkan foto admin dari URL
        Glide.with(holder.itemView.context)
            .load(admin.foto)
            .placeholder(R.drawable.common_google_signin_btn_icon_light)  // Gambar sementara jika foto tidak tersedia
            .into(holder.binding.foto1)
    }

    override fun getItemCount(): Int = adminList.size
}
