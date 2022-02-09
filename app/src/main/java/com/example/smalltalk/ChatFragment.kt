package com.example.smalltalk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date

class ChatFragment : Fragment() {
    private lateinit var chatcyclerView: RecyclerView
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var myAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatList = listOf(
            ChatMessage("Andreas", Date(11111), "Hei!"),
            ChatMessage(
                "Andreas",
                Date(11111),
                "What the fuck did you just fucking say about me, you little shit? Ill have you know I graduated top of my class in the Navy Seals, and Ive been involved in numerous secret raids on Al-Quaeda, and I have over 300 confirmed kills. I am trained in gorilla warfare and Im the top sniper in the entire US armed forces. You are nothing to me but just another target. I will wipe you the fuck out with precision the likes of which has never been seen before on this Earth, mark my fucking words. You think you can get away with saying that shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of spies across the USA and your IP is being traced right now so you better prepare for the storm, maggot. The storm that wipes out the pathetic little thing you call your life. Youre fucking dead, kid. I can be anywhere, anytime, and I can kill you in over seven hundred ways, and thats just with my bare hands. Not only am I extensively trained in unarmed combat, but I have access to the entire arsenal of the United States Marine Corps and I will use it to its full extent to wipe your miserable ass off the face of the continent, you little shit. If only you could have known what unholy retribution your little clever comment was about to bring down upon you, maybe you would have held your fucking tongue. But you couldnt, you didnt, and now youre paying the price, you goddamn idiot. I will shit fury all over you and you will drown in it. Youre fucking dead, kiddo."
            ),
            ChatMessage("Ikke Andreas", Date(11111), "Ok"),
            ChatMessage("Andreas", Date(11111), "Hei!"),
            ChatMessage("Andreas", Date(11111), "Hei!!"),
            ChatMessage("Ikke Andreas", Date(11111), "Hei"),
            ChatMessage("Andreas", Date(11111), "Hei!"),
            ChatMessage("Andreas", Date(11111), "Hei!!"),
            ChatMessage("Ikke Andreas", Date(11111), "Hei"),
            ChatMessage("Andreas", Date(11111), "Hei!"),
            ChatMessage("Andreas", Date(11111), "Hei!!"),
            ChatMessage("Ikke Andreas", Date(11111), "Hei"),
            ChatMessage("Andreas", Date(11111), "Hei!"),
            ChatMessage("Andreas", Date(11111), "Hei!!"),
            ChatMessage("Ikke Andreas", Date(11111), "Hei"),
            ChatMessage("Andreas", Date(11111), "Hei!"),
            ChatMessage("Andreas", Date(11111), "Hei!!"),
            ChatMessage("Ikke Andreas", Date(11111), "Hei"),
        )

        chatcyclerView = view.findViewById(R.id.layout_chat_recyclerview)
        myLayoutManager = LinearLayoutManager(activity)

        myAdapter = ChatAdapter(chatList, "Andreas")

        chatcyclerView.layoutManager = myLayoutManager
        chatcyclerView.adapter = myAdapter

        chatcyclerView.scrollToPosition(chatList.size - 1)
    }
}