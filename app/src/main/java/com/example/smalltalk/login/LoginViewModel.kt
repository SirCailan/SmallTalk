package com.example.smalltalk.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.beust.klaxon.Klaxon
import com.example.smalltalk.baseUrl
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDao
import com.example.smalltalk.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository()

    val signedIn: MutableLiveData<Boolean> = MutableLiveData()
    val errorText: MutableLiveData<String> = MutableLiveData()
    val pleaseWait: MutableLiveData<Boolean> = MutableLiveData()


    /*fun checkCredentials(username: String, password: String): User? {
        return if (username == "Cailan" && password == "passord123") {
            val currentUser =
                User(userName = "Cailan", firstName = "Andreas", lastName = "Thomson")
            currentUser
        } else {
            null
        }
    } */

    fun signIn(username: String, password: String) {
        pleaseWait.postValue(true)

        userRepository.fetchUser(username, password) { success, errorString ->
            pleaseWait.postValue(false)
            if (success) {
                signedIn.postValue(true)
            } else {
                errorText.postValue(errorString)
            }
        }
    }

}