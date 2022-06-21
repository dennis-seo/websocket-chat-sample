package com.jeffrey.chatsample.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeffrey.chatsample.databinding.MainFragmentBinding
import com.jeffrey.melonchatserver.ChatServerListener
import com.jeffrey.melonchatserver.MelonWebSocketServer

class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(TAG, "onCreateView")
        return MainFragmentBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        viewModel.messageList.observe(viewLifecycleOwner, Observer { messages ->
            for(message in messages) {
                Log.d(TAG, "observe message : ${message.type}")
            }
            if(::chatAdapter.isInitialized) {
                Log.d(TAG, "updateMessageList()")
                chatAdapter.recyclerViewItems = messages
            }
        })

        val chatServerListener = object : ChatServerListener {
            override fun onMessage(message: String) {
                Log.d(TAG, "server message : $message")
                binding.tvServerState.text = message
            }
        }

        binding.serverSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                Log.d(TAG, "start server")
                viewModel.startServer(chatServerListener)
            } else {
                Log.d(TAG, "close server")
                viewModel.closeServer()
            }
        }

        binding.btnConnect.setOnClickListener {
            Toast.makeText(it.context, "connect", Toast.LENGTH_SHORT).show()
            viewModel.connect()
        }

        binding.btnDisconnect.setOnClickListener {
            viewModel.disconnect()
            viewModel.messageList.value?.clear()
        }

        binding.btnSend.setOnClickListener {
            viewModel.sendMessage(binding.edMessage.text.toString())
            binding.edMessage.text?.clear()
        }

        chatAdapter = ChatAdapter()
        binding.recyclerChat.layoutManager = LinearLayoutManager(context)
        binding.recyclerChat.adapter = chatAdapter
    }
}