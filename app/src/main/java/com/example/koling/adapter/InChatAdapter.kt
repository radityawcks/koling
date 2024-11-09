package com.example.koling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.koling.Message
import androidx.recyclerview.widget.RecyclerView
import com.example.koling.databinding.ItemMessageBinding

class InChatAdapter(private val messagesList: List<Message>) : RecyclerView.Adapter<InChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messagesList[position]
        holder.binding.tvMessageContent.text = message.text
        holder.binding.tvSenderName.text = message.sender
    }

    override fun getItemCount(): Int = messagesList.size
}
