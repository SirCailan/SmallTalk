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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private lateinit var userDao: UserDao

    val activeUser: MutableLiveData<User> = MutableLiveData()
    val errorText: MutableLiveData<String> = MutableLiveData()
    val pleaseWait: MutableLiveData<Boolean> = MutableLiveData()

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

    fun fetchUser(queue: RequestQueue, username: String, password: String) {
        //val url = baseUrl + "login?userName=$username&password=$password"
        val url = "https://run.mocky.io/v3/bac8c467-46e2-4eba-9511-e8b94def3d17"

        val userRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val user = Klaxon().parse<User>(response)

                if (user != null) {
                    saveUser(user)
                } else {
                    errorText.postValue("Kunne ikke hente bruker fra API")
                }

                pleaseWait.postValue(false)
            },
            { error ->
                val errorString = when (error.networkResponse.statusCode) {
                    400 -> "400 - Feil input"
                    401 -> "401 - Feil brukernavn / passord"
                    500 -> "500 - Serverfeil"
                    else -> "Ukjent feil"
                }

                errorText.postValue(errorString)

                //Reaktiverer logg inn-knappen og skrur av progress bar
                pleaseWait.postValue(false)
            }
        )

        queue.add(userRequest)
    }

    private fun saveUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAllUsers()

            userDao.addUser(user)

            activeUser.postValue(user)
        }.start()
    }

}