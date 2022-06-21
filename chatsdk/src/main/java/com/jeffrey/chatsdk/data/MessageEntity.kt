package com.jeffrey.chatsdk.data


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/17
*
*/

abstract class MessageEntity {
    abstract var id: String
    abstract var type: String
}

data class InitMessageEntity(
    override var id: String,
    override var type: String,
    val roomId: String,
    val chatMeta: ChatMeta,
    val noticeMessages: Any,
    val userFixedMessages: Any,
    val artistFixedMessages: Any
) : MessageEntity()
data class ChatMeta(
    val id: String,
    val name: String,
    val startDt: String,
    val endDt: String,
    val systemGuide: String,
    val userGuide: String,
    val abuseGuide: String,
    val spamGuide: String,
    val spamRule: String,
    val userWelcomeGuide: String,
    val artistWelcomeGuide: String,
    val maxUserCountPerRoom: Int,
    val userChatFixedDuration: Int,
    val maxUserChatFixedCount: Int,
    val artistChatFixedDuration: Int,
    val maxArtistChatFixedCount: Int,
    val createdDt: String
)

data class UserMessageEntity(
    override var id: String,
    override var type: String,
    val rowMessage: String,
    val message: String,
    val sendTime: String,
    val includeBannedWord: Boolean,
    val fromUser: FromUser,
    val createdDt: String,
) : MessageEntity()

data class ArtistMessageEntity(
    override var id: String,
    override var type: String,
    val message: String,
    val sendTime: String,
    val fromUser: FromUser,
    val createdDt: String
) : MessageEntity()
data class FromUser(
    val id: String,
    val name: String,
    val profileImage: String,
    val gradle: String,
)

data class NoticeMessageEntity(
    override var id: String,
    override var type: String,
    val message: String,
    val actionTitle: String,
    val actionUrl: String,
    val createdDt: String
) : MessageEntity()