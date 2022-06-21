package com.jeffrey.melonchatserver

import android.util.Log
import org.java_websocket.WebSocket
import org.java_websocket.drafts.Draft
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.InetSocketAddress


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/15
*
*/
class MelonWebSocketServer : WebSocketServer {
    companion object {
        const val TAG = "MelonWebSocketServer"
        private var thread: Thread? = null
        private var melonSocketServer: MelonWebSocketServer? = null
        private var chatServerListener: ChatServerListener? = null

        fun startWebSocketServer(listenerChat: ChatServerListener) {
            Log.d(TAG, "startWebSocketServer()")
            chatServerListener = listenerChat
            thread = Thread {
                // 843 flash policy port
                melonSocketServer = MelonWebSocketServer(8887).apply {
                    start()
                }
                chatServerListener?.onMessage("ChatServer started on port: " + melonSocketServer?.port)
            }
            thread?.start()
        }

        fun stopWebSocketServer() {
            melonSocketServer?.stop()
            melonSocketServer = null
            chatServerListener?.onMessage("stopWebSocketServer()")
        }
    }

    constructor(port: Int) : super(InetSocketAddress(port)) {}
    constructor(address: InetSocketAddress?) : super(address) {}
    constructor(port: Int, draft: Draft_6455) : super(
        InetSocketAddress(port),
        listOf<Draft>(draft)) {}

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        println("Melon Chat Server onOpen()!!")
        println(InitMessage.getJSonMessage())
        conn?.send(InitMessage.getJSonMessage())
//        conn?.let {
//            broadcast("$it +' entered")
//        }
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
//        conn?.let {
//            broadcast("$it has left te room")
//        }
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        println("Melon Chat Server onMessage() : $message")
        println("Melon Chat Server onMessage() : ${CustomMessage.getJSonMessage(message)}")
//        broadcast("Server -> \"$message\" -> Client")
        broadcast(CustomMessage.getJSonMessage(message))
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        println("Melon Chat Server Error!! : $ex")
        chatServerListener?.onMessage("exception : $ex")
//        Log.i(TAG, "Melon Chat Server Error!! : $ex")
    }

    override fun onStart() {
        println("Melon Chat Server Started!!")
//        Log.i(TAG, "Melon Chat Server Started!!")
    }
}
