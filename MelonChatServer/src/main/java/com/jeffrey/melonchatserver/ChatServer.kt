package com.jeffrey.melonchatserver

import android.util.Log
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException


/*
* Copyright (C) 2022 Kakao corp. All rights reserved.
*
* Created by Jeffrey.bbongs on 2022/06/14
*
*/
@Deprecated("socket server")
class ChatServer {

    companion object {
        const val TAG = "ChatServer"
    }
    private var serverSocket: ServerSocket? = null
    private var socket: Socket? = null
    private var dataInputStream: DataInputStream? = null
    private var chatServerListener: ChatServerListener? = null

    fun serverStart(listenerChat: ChatServerListener) {
        chatServerListener = listenerChat
        serverSocket = ServerSocket(12121)
        chatServerListener?.onMessage("Start Server : socket open")
        try {
            socket = serverSocket?.accept()
            Log.e(TAG, "socket : ${socket?.isConnected}")
            chatServerListener?.onMessage("Start Server : accept()")

            socket?.let { socket ->
                dataInputStream = DataInputStream(socket.getInputStream())
                val dataOutputStream = DataOutputStream(socket.getOutputStream())

                val bufferedReader = BufferedReader(InputStreamReader(System.`in`))

                var str: String? = null
                var strEcho: String? = null

                Log.d(TAG, "input stream : ${socket.getInputStream().read()}")

                chatServerListener?.onMessage("Waiting for client's Reply...")
                while (str != "stop") {
                    str = dataInputStream?.readUTF()
                    Log.d(TAG, "str : $str")
                    chatServerListener?.onMessage("client call : $str")
                    println("Client: $str")
                    println("Enter Message:")
                    strEcho = bufferedReader.readLine()
                    dataOutputStream.writeUTF(strEcho)
                    chatServerListener?.onMessage("server send : $strEcho")
                    dataOutputStream.flush()
                }
            }

//            val chatServerThread = ChatServerThread(dataInputStream!!, dataOutputStream, chatServerListener)
//            chatServerThread.start()
        } catch (exception: SocketException) {
            Log.e(TAG, "exception : $exception")
        }
    }

    internal class ChatServerThread(val dataInputStream: DataInputStream,
                                    val dataOutputStream: DataOutputStream,
                                    val chatServerListener: ChatServerListener?) : Thread() {
        private var str: String? = null
        private var strEcho: String? = null
        private val bufferedReader = BufferedReader(InputStreamReader(System.`in`))

        override fun run() {
            while (str != "stop") {
                str = dataInputStream?.readUTF()
                Log.d(TAG, "str : $str")
                chatServerListener?.onMessage("client call : $str")
                println("Client: $str")
                println("Enter Message:")
                strEcho = bufferedReader.readLine()
                dataOutputStream.writeUTF(strEcho)
                chatServerListener?.onMessage("server send : $strEcho")
                dataOutputStream.flush()
            }
        }
    }


    fun stopServer() {
        Log.d("bbong", "stopServer")
        if (serverSocket == null) {
            Log.d("bbong", "serverSocket is null")
        }
        dataInputStream?.close()
        if(socket?.isConnected == true) {
            socket?.close()
        }
        serverSocket?.close()
        chatServerListener?.onMessage("Server Closed")
    }
}