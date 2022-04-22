package com.example.smalltalk.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.example.smalltalk.ChatAdapter
import com.example.smalltalk.ChatMessage
import com.example.smalltalk.R
import java.util.*

class ChatFragment : Fragment() {
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var myAdapter: ChatAdapter
    private lateinit var buttonSettings: ImageView
    private lateinit var buttonSendMessage: ImageButton
    private lateinit var messageInput: EditText
    private lateinit var progressBar: ProgressBar

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.buildDatabase(requireContext())

        viewModel.getCurrentUser()

        viewModel.fetchMessages(Volley.newRequestQueue(requireContext()))

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSettings = view.findViewById(R.id.chat_button_settings)
        buttonSendMessage = view.findViewById(R.id.chat_button_send_message)
        messageInput = view.findViewById(R.id.chat_message_input)
        progressBar = view.findViewById(R.id.chat_messages_progressbar)

        myRecyclerView = view.findViewById(R.id.chat_recyclerview_messages)
        myLayoutManager = LinearLayoutManager(activity)
        myRecyclerView.layoutManager = myLayoutManager

        myAdapter = ChatAdapter(viewModel.chatList)
        myRecyclerView.adapter = myAdapter

        myRecyclerView.scrollToPosition(viewModel.chatList.size - 1)

        setListeners()
    }

    private fun setListeners() {
        buttonSettings.setOnClickListener {
            //Navigates to profile / settings page.
            findNavController().navigate(ChatFragmentDirections.actionChatFragmentToProfileFragment())
        }

        buttonSendMessage.setOnClickListener {
            val input = messageInput.text.toString()
            messageInput.text.clear()

            if (input.isNotEmpty()) {
                viewModel.sendMessage(Volley.newRequestQueue(requireContext()), input)

                val user = viewModel.signedInUser.value

                myAdapter.addItem(
                    ChatMessage(
                        user?.id ?: "No ID",
                        input,
                        user?.userName ?: "No Username",
                        Date().time
                    )
                )
                myRecyclerView.scrollToPosition(myAdapter.getDataset().size - 1)
            }
        }

        viewModel.pleaseWait.observe(viewLifecycleOwner) { loading ->
            //Shows progressbar when loading messages, and hides it when finished.
            when (loading) {
                true -> {
                    progressBar.visibility = View.VISIBLE
                }
                false -> {
                    progressBar.visibility = View.GONE
                }
            }
        }

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            myAdapter.switchDataset(messages)
            myRecyclerView.scrollToPosition(messages.size - 1)
        }

        viewModel.errorText.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.signedInUser.observe(viewLifecycleOwner) { user ->
            myAdapter.currentUserId = user.id
        }
    }
}