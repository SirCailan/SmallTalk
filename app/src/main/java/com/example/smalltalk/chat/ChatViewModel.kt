package com.example.smalltalk.chat

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.beust.klaxon.Klaxon
import com.example.smalltalk.ChatMessage
import com.example.smalltalk.baseUrl
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDao
import com.example.smalltalk.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.sql.Date

class ChatViewModel : ViewModel() {
    private val userRepository = UserRepository()

    val signedInUser: MutableLiveData<User> = MutableLiveData()
    val fetchSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val postSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val messages: MutableLiveData<List<ChatMessage>> = MutableLiveData()
    val pleaseWait: MutableLiveData<Boolean> = MutableLiveData()
    val errorText: MutableLiveData<String> = MutableLiveData()

    val chatList = listOf(
        ChatMessage("gawreiu13", "Første melding", "andreas", 11111111110),
        ChatMessage(
            "andreas123",
            "What the fuck did you just fucking say about me, you little shit? Ill have you know I graduated top of my class in the Navy Seals, and Ive been involved in numerous secret raids on Al-Quaeda, and I have over 300 confirmed kills. I am trained in gorilla warfare and Im the top sniper in the entire US armed forces. You are nothing to me but just another target. I will wipe you the fuck out with precision the likes of which has never been seen before on this Earth, mark my fucking words. You think you can get away with saying that shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of spies across the USA and your IP is being traced right now so you better prepare for the storm, maggot. The storm that wipes out the pathetic little thing you call your life. Youre fucking dead, kid. I can be anywhere, anytime, and I can kill you in over seven hundred ways, and thats just with my bare hands. Not only am I extensively trained in unarmed combat, but I have access to the entire arsenal of the United States Marine Corps and I will use it to its full extent to wipe your miserable ass off the face of the continent, you little shit. If only you could have known what unholy retribution your little clever comment was about to bring down upon you, maybe you would have held your fucking tongue. But you couldnt, you didnt, and now youre paying the price, you goddamn idiot. I will shit fury all over you and you will drown in it. Youre fucking dead, kiddo.",
            "pøbel123",
            11111111111
        ),
        ChatMessage("gawreiu13", "Ok", "andreas", 11111111112),
    )

    fun getMessages() {
        signedInUser.value?.id?.let { userId ->
            userRepository.fetchMessages(userId) { success, errorString, fetchedMessages ->
                pleaseWait.postValue(false)
                fetchSuccess.postValue(success)

                if (success) {
                    messages.postValue(fetchedMessages)
                } else {
                    errorText.postValue(errorString)
                }
            }
        }
    }

    fun sendMessage(messageText: String) {
        signedInUser.value?.id?.let { userId ->
            userRepository.sendMessage(messageText, userId) { success, errorString ->
                postSuccess.postValue(success)

                if (success) {
                    getMessages()
                } else {
                    errorText.postValue(errorString)
                }
            }
        }
    }

    fun getUser() {
        userRepository.fetchUser { user ->
            signedInUser.postValue(user)
        }
    }

}