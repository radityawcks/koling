package com.example.koling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koling.DataChat
import com.example.koling.databinding.ItemChatBinding
import com.google.firebase.database.collection.R

class ChatAdapter(private val chatList: List<DataChat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    var onItemClick: ((DataChat) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.binding.username.text = chat.username
        holder.binding.pesan.text = chat.pesan

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(chat)  // Memanggil listener ketika item diklik
        }
    }

    override fun getItemCount(): Int = chatList.size

    class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root)
}
