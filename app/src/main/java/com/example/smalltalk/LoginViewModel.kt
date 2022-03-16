package com.example.smalltalk

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.beust.klaxon.Klaxon
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private lateinit var userDao: UserDao

    fun buildDatabase(context: Context) {
        val database =
            Room.databaseBuilder(context, AppDatabase::class.java, "SmallTalk_DATABASE")
                .build()

        userDao = database.userDao()
    }

    /*fun checkCredentials(username: String, password: String): User? {
        return if (username == "Cailan" && password == "passord123") {
            val currentUser =
                User(userName = "Cailan", firstName = "Andreas", lastName = "Thomson")
            currentUser
        } else {
            null
        }
    } */

    fun checkApi(queue: RequestQueue, username: String, password: String, callback: (User?) -> Unit) {
        val url = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/login?userName=$username&password=$password"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val user = Klaxon().parse<User>(response)
                callback(user)
            },
            { error ->
                callback(null)
            }
        )

        queue.add(stringRequest)
    }

    fun saveUser(currentUser: User, callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAllUsers()

            userDao.addUser(currentUser)

            callback()
        }.start()
    }

}