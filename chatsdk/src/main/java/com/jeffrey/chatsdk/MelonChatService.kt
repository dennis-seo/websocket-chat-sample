package com.jeffrey.chatsdk

import android.util.Log
import com.jeffrey.chatsdk.data.ChatState
import com.jeffrey.chatsdk.data.MessageEntity
import com.jeffrey.chatsdk.data.MessageFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.collect
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/09
*
*/
class MelonChatService {
    companion object {
        private const val TAG = "MelonChatService"
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

    private lateinit var client: OkHttpClient
    private var webSocket: WebSocket? = null

    private var chatSdkListener: ChatSdkListener? = null

    fun connect(url: String, listener: ChatSdkListener) {
        client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        chatSdkListener = listener

        val request: Request = Request.Builder()
            .url(url)
            .build()
        webSocket = client.newWebSocket(request, MelonSocketListener())
    }

    fun disconnect() {
        webSocket?.close(NORMAL_CLOSURE_STATUS, null)
    }

    fun send(message: String) {
        Log.d(TAG, "send message : $message")
        if (webSocket == null) {
            Log.d(TAG, "webSocket is null")
        }
        webSocket?.send(message)
    }


    inner class MelonSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d(TAG,"onOpen() : $response")
            chatSdkListener?.onChangeState(ChatState.Opened)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d(TAG,"Receiving : $text")
            chatSdkListener?.onMessage(listOf(MessageFactory().createMessage(text)))
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            // ignore
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(TAG,"Closing : $code / $reason")
            chatSdkListener?.onChangeState(ChatState.Closed)
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            webSocket.cancel()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e(TAG,"Error : " + t.message)
            chatSdkListener?.onChangeState(ChatState.Failed(t.message))
        }
    }
}