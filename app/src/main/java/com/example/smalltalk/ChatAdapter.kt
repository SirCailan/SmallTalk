package com.example.smalltalk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private var dataSet: List<ChatMessage> = listOf(),
    var currentUserId: String? = "N/A"
) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open lateinit var senderImage: ImageView
        open lateinit var senderName: TextView
        open lateinit var timeSent: TextView
        open lateinit var messageText: TextView
    }

    inner class SelfMessageHolder(view: View) : MessageViewHolder(view) {
        override var senderImage: ImageView = view.findViewById(R.id.user_image_self)
        override var senderName: TextView = view.findViewById(R.id.user_name_self)
        override var timeSent: TextView = view.findViewById(R.id.message_time_self)
        override var messageText: TextView = view.findViewById(R.id.message_text_self)
    }

    inner class OthersMessageHolder(view: View) : MessageViewHolder(view) {
        override var senderImage: ImageView = view.findViewById(R.id.user_image_others)
        override var senderName: TextView = view.findViewById(R.id.user_name_others)
        override var timeSent: TextView = view.findViewById(R.id.message_time_others)
        override var messageText: TextView = view.findViewById(R.id.message_text_others)
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].userId == currentUserId) {
            0
        } else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val context = parent.context

        val cellView = if (viewType == 0) {
            LayoutInflater.from(context)
                .inflate(R.layout.chat_bubble_self, parent, false)
        } else {
            LayoutInflater.from(context)
                .inflate(R.layout.chat_bubble_others, parent, false)
        }

        val params: ViewGroup.LayoutParams = cellView.layoutParams
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        cellView.layoutParams = params

        return if (viewType == 0) {
            SelfMessageHolder(cellView)
        } else {
            OthersMessageHolder(cellView)
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val chatMessage = dataSet[position]

        holder.senderName.text = chatMessage.userName
        holder.messageText.text = chatMessage.message

        val simpleDate = SimpleDateFormat("hh:mm dd/MM/yyyy", Locale.getDefault())

        holder.timeSent.text = simpleDate.format(chatMessage.timestamp)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun switchDataset(newDataset: List<ChatMessage>) {
        dataSet = newDataset
        notifyDataSetChanged()
    }

    fun getDataset(): List<ChatMessage> {
        return dataSet
    }

    fun addItem(newItem: ChatMessage) {
        val newDataSet = dataSet + newItem
        dataSet = newDataSet
        notifyItemInserted(dataSet.size - 1)
    }

}