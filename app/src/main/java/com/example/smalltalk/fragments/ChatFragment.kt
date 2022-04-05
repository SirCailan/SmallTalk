package com.example.smalltalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalk.ChatAdapter
import com.example.smalltalk.viewmodels.ChatViewModel
import com.example.smalltalk.R

class ChatFragment : Fragment() {
    private lateinit var chatcyclerView: RecyclerView
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var myAdapter: ChatAdapter
    private lateinit var buttonSettings: ImageView
    private lateinit var buttonSendMessage: ImageView
    private lateinit var messageInput: EditText

    private val model: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSettings = view.findViewById(R.id.chat_button_settings)
        buttonSendMessage = view.findViewById(R.id.chat_button_send_message)
        messageInput = view.findViewById(R.id.chat_message_input)

        chatcyclerView = view.findViewById(R.id.chat_recyclerview_messages)
        myLayoutManager = LinearLayoutManager(activity)

        myAdapter = ChatAdapter(model.chatList, model.currentUser)

        chatcyclerView.layoutManager = myLayoutManager
        chatcyclerView.adapter = myAdapter

        chatcyclerView.scrollToPosition(model.chatList.size - 1)

        setButtons()
    }

    private fun setButtons() {
        buttonSettings.setOnClickListener {
            findNavController().navigate(ChatFragmentDirections.actionChatFragmentToProfileFragment())
        }
    }
}