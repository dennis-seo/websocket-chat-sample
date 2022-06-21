package com.jeffrey.chatsdk.data

import com.google.gson.Gson


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/10
*
*/
@Deprecated ("")
sealed class ChatItem {
    var messageEntity: MessageEntity? = null
    data class SystemChat(val rowData: String) : ChatItem() {
        init {
            messageEntity = Gson().fromJson(rowData, NoticeMessageEntity::class.java)
        }
    }
    data class MyChatItem(val rowData: String) : ChatItem()
    data class OtherChatItem(val rowData: String) : ChatItem()
}