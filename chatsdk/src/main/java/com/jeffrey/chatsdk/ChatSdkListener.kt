package com.jeffrey.chatsdk

import com.jeffrey.chatsdk.data.ChatState
import com.jeffrey.chatsdk.data.MessageEntity


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/10
*
*/
interface ChatSdkListener {
    fun onMessage(messageItems: List<MessageEntity>)
    fun onChangeState(event: ChatState)
}