package com.jeffrey.chatsdk.data

import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/17
*
*/
class MessageFactory {
    private val TYPE_SEND_MESSAGE = "SEND_MESSAGE"
    private val TYPE_INIT_MESSAGE = "INIT_MESSAGE"
    private val TYPE_USER_MESSAGE = "USER_MESSAGE"
    private val TYPE_USER_FIXED_MESSAGE = "USER_FIXED_MESSAGE"
    private val TYPE_ARTIST_MESSAGE = "ARTIST_MESSAGE"
    private val TYPE_ARTIST_FIXED_MESSAGE = "ARTIST_FIXED_MESSAGE"
    private val TYPE_NOTICE_MESSAGE = "NOTICE_MESSAGE"
    private val TYPE_NOTICE_FIXED_MESSAGE = "NOTICE_FIXED_MESSAGE"

    fun createMessage(rowData: String): MessageEntity {
        try {
            val type = JSONObject(rowData).getString("type")

            return when (type) {
                TYPE_SEND_MESSAGE -> Gson().fromJson(rowData, UserMessageEntity::class.java)
                TYPE_INIT_MESSAGE -> Gson().fromJson(rowData, InitMessageEntity::class.java)
                TYPE_USER_MESSAGE, TYPE_USER_FIXED_MESSAGE -> Gson().fromJson(rowData, UserMessageEntity::class.java)
                TYPE_ARTIST_MESSAGE, TYPE_ARTIST_FIXED_MESSAGE -> Gson().fromJson(rowData, ArtistMessageEntity::class.java)
                TYPE_NOTICE_MESSAGE, TYPE_NOTICE_FIXED_MESSAGE -> Gson().fromJson(rowData, NoticeMessageEntity::class.java)
                else -> Gson().fromJson(rowData, MessageEntity::class.java)
            }
        } catch (exception: Exception) {
            Log.e("MessageFactory", "exception : $exception")
            Log.e("MessageFactory", "rowData : $rowData")
            return Gson().fromJson(rowData, MessageEntity::class.java)
        }
    }
}