package com.jeffrey.chatsample.ui.main

import android.os.Message
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeffrey.chatsdk.ChatSdkListener
import com.jeffrey.chatsdk.data.ChatState
import com.jeffrey.chatsdk.MelonChatService
import com.jeffrey.chatsdk.data.ChatItem
import com.jeffrey.chatsdk.data.MessageEntity
import com.jeffrey.melonchatserver.ChatServer
import com.jeffrey.melonchatserver.ChatServerListener
import com.jeffrey.melonchatserver.MelonWebSocketServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    // server
    private var chatServer: ChatServer? = null

    private lateinit var melonChatService: MelonChatService
    private lateinit var melonChatListener: ChatSdkListener

    private val _messageList = MutableLiveData<ArrayList<MessageEntity>>()
    val messageList: LiveData<ArrayList<MessageEntity>> get() = _messageList


    fun startServer(chatServerListener: ChatServerListener) {
        Log.d(TAG, "startServer()")
        MelonWebSocketServer.startWebSocketServer(chatServerListener)
        // 소켓서버라서 실패! (WebSocket 서버로 열어야 했음)
//        CoroutineScope(Dispatchers.IO).launch {
//            chatServer = ChatServer()
//            chatServer?.serverStart(chatServerListener)
//        }
    }

    fun closeServer() {
        Log.d(TAG, "closeServer()")
        MelonWebSocketServer.stopWebSocketServer()
        // 소켓서버라서 실패! (WebSocket 서버로 열어야 했음)
//        CoroutineScope(Dispatchers.IO).launch {
//            chatServer?.stopServer()
//            chatServer = null
//        }
    }


    fun connect() {
        Log.d(TAG, "connect()")
        melonChatService = MelonChatService()
        melonChatListener = object : ChatSdkListener {
            override fun onMessage(messageItems: List<MessageEntity>) {
                for(messageItem in messageItems) {
                    Log.d(TAG, "onMessage : ${messageItem.type}")

                }
                val temp = _messageList.value
                if(temp == null) {
                    Log.d(TAG, "temp is null")
                    _messageList.postValue(ArrayList(messageItems))
                } else {
                    Log.d(TAG, "temp is not null")
                    temp.addAll(messageItems)
                    _messageList.postValue(temp!!)
                }
            }

            override fun onChangeState(event: ChatState) {
                Log.i(TAG, "onChangeState : ${event.state}")

                when(event) {
//                    ChatState.Opened ->
//                        melonChatService.send("test")
                }
            }

        }
//        melonChatService.connect("wss://pubwss.bithumb.com/pub/ws", melonChatListener)
//        melonChatService.connect("ws://192.168.0.11:843", melonChatListener)
        melonChatService.connect("ws://localhost:8887", melonChatListener)
//        melonChatService.connect("ws://localhost:843", melonChatListener)
    }

    fun sendMessage(message: String) {
        if(::melonChatService.isInitialized) {
            melonChatService.send(message)
        }
    }
    fun disconnect() {
        Log.d(TAG, "disconnect()")
        if(::melonChatService.isInitialized) {
            melonChatService.disconnect()
        }
    }

}