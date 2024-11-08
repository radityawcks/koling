package com.example.koling.adapter

import android.content.Intent
import com.example.koling.Curhat
import com.example.koling.databinding.ItemCurhatBinding

// AdminAdapter.kt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koling.model.Admin
import com.bumptech.glide.Glide
import com.example.koling.InChat
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
        Glide.with(holder.itemView.context).load(admin.foto).into(holder.binding.foto1)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, InChat::class.java)
            intent.putExtra("namaBK", admin.username)
            intent.putExtra("fotoUrl", admin.foto)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = adminList.size
}
