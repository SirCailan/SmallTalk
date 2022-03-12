package com.example.smalltalk

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
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

    fun checkCredentials(username: String, password: String): User? {
        return if (username == "Cailan" && password == "passord123") {
            val currentUser =
                User(userName = "Cailan", firstName = "Andreas", lastName = "Thomson")
            currentUser
        } else {
            null
        }
    }

    fun signIn(currentUser: User, callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAllUsers()

            userDao.addUser(currentUser)

            callback()
        }.start()
    }

}