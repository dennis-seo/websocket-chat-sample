//package com.jeffrey.chatsdk
//
//import android.util.Log
//import com.jeffrey.chatsdk.data.ChatState
//import com.jeffrey.chatsdk.data.MessageEntity
//import com.jeffrey.chatsdk.data.MessageFactory
//import kotlinx.coroutines.*
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.channels.ReceiveChannel
//import kotlinx.coroutines.channels.produce
//import kotlinx.coroutines.flow.MutableStateFlow
//import okhttp3.Response
//import okhttp3.WebSocket
//import okhttp3.WebSocketListener
//import okio.ByteString
//
//
///*
//* Copyright (C) 2022 Kakao corp. All rights reserved.
//*
//* Created by Jeffrey.bbongs on 2022/06/09
//*
//*/
//open class MelonSocketListener : WebSocketListener() {
//    companion object {
//        private const val TAG = "MelonSocketListener"
//        private const val NORMAL_CLOSURE_STATUS = 1000
//    }
//
//
//    private val _chatState = MutableStateFlow<ChatState>(ChatState.Initialized)
//    var chatState = _chatState
//
//    var channel = Channel<List<MessageEntity>>()
////    private val _messgaeList = MutableStateFlow(ArrayList<MessageEntity>)()
//
//    override fun onOpen(webSocket: WebSocket, response: Response) {
//        _chatState.value = ChatState.Opened
//        Log.d(TAG,"onOpen()")
//
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
//    override fun onMessage(webSocket: WebSocket, text: String) {
//        Log.d(TAG,"Receiving : $text")
//        GlobalScope.launch {
//            Log.d(TAG,"aaa")
//            channel.send(listOf(MessageFactory().createMessage(text)))
//            channel.close()
//        }
//
////        _chatState.value = ChatState.OnMessage(text)
//    }
//
//    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
////        Log.d(TAG, "Receiving bytes : $bytes")
//    }
//
//    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        Log.d(TAG,"Closing : $code / $reason")
//        channel.close()
//        _chatState.value = ChatState.Closed
//        webSocket.close(NORMAL_CLOSURE_STATUS, null)
//        webSocket.cancel()
//    }
//
//    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        Log.e(TAG,"Error : " + t.message)
//        channel.close(t)
//        _chatState.value = ChatState.Failed(t.message)
//    }
//}