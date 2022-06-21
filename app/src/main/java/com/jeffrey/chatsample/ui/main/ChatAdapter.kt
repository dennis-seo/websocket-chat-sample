package com.jeffrey.chatsample.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jeffrey.chatsample.databinding.ViewMessageBinding
import com.jeffrey.chatsdk.data.InitMessageEntity
import com.jeffrey.chatsdk.data.MessageEntity
import com.jeffrey.chatsdk.data.NoticeMessageEntity
import com.jeffrey.chatsdk.data.UserMessageEntity
import kotlin.properties.Delegates


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/17
*
*/
class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MessageHolder>(), AutoUpdatableAdapter {

    companion object {
        const val TAG = "ChatAdapter"
    }

    var recyclerViewItems: List<MessageEntity> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { oldMessageEntity, newMessageEntity ->
            oldMessageEntity.id == newMessageEntity.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val binding = ViewMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val messageEntity = recyclerViewItems[position]
        when(messageEntity) {
            is InitMessageEntity -> {
                Log.i(TAG, "InitMessageEntity message : ${messageEntity.type}")
                holder.binding.tvName.text = messageEntity.roomId
                holder.binding.tvMessage.text = messageEntity.chatMeta.systemGuide
            }
            is NoticeMessageEntity -> {
                Log.i(TAG, "NoticeMessageEntity message : ${messageEntity.message}")
            }
            is UserMessageEntity -> {
                Log.i(TAG, "UserMessageEntity message : ${messageEntity.message}")
                holder.binding.tvName.text = messageEntity.fromUser.name
                holder.binding.tvMessage.text = messageEntity.message
            }
        }
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    inner class MessageHolder(val binding: ViewMessageBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}