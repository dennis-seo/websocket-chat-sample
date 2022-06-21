package com.jeffrey.chatsdk.data


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/10
*
*/
sealed class ChatState(val state: String) {
    companion object {
        const val INIT = "INIT"
        const val OPENED = "OPENED"
        const val CLOSED = "CLOSED"
        const val FAILED = "FAILED"
    }
    object Initialized : ChatState(INIT)
    object Opened : ChatState(OPENED)
    object Closed : ChatState(CLOSED)
    data class Failed(val reason: String?) : ChatState(FAILED)
}