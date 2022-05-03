package com.example.smalltalk.repositories

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.smalltalk.ChatMessage
import com.example.smalltalk.SmallTalkApp
import com.example.smalltalk.baseUrl
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository {
    private val appContext = SmallTalkApp.application.applicationContext
    private val userDao = AppDatabase.getInstance(appContext).userDao()
    private val requestQueue = Volley.newRequestQueue(appContext)

    fun fetchUser(username: String, password: String, callback: (Boolean, String) -> Unit) {
        val url = baseUrl + "login?userName=$username&password=$password"
        //val url = "https://run.mocky.io/v3/bac8c467-46e2-4eba-9511-e8b94def3d17"

        val userRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val user = Klaxon().parse<User>(response)

                if (user != null) {
                    saveUser(user)
                    callback(true, "")
                } else {
                    callback(false, "Kunne ikke hente bruker")
                }


            },
            { error ->
                val errorString = when (error.networkResponse.statusCode) {
                    400 -> "400 - Feil input"
                    401 -> "401 - Feil brukernavn / passord"
                    500 -> "500 - Serverfeil"
                    else -> "Ukjent feil"
                }

                callback(false, errorString)
            }
        )

        requestQueue.add(userRequest)
    }

    fun fetchMessages(userId: String, callback: (Boolean, String, List<ChatMessage>) -> Unit) {
        var apiSuccess = false
        var errorString = ""

        val url = baseUrl + "messages?userId=" + userId

        val messagesRequest = StringRequest(
            Request.Method.GET,
            url,
            { json ->
                val parsedResponse = Klaxon().parseArray<ChatMessage>(json) ?: listOf()

                if (parsedResponse.isNotEmpty()) {
                    apiSuccess = true
                } else {
                    errorString = "Kunne ikke hente meldinger"
                }

                callback(apiSuccess, errorString, parsedResponse)
            },
            { error ->
                errorString = when (error.networkResponse.statusCode) {
                    400 -> "400 - Feil input"
                    401 -> "401 - Bruker har ikke tilgang"
                    500 -> "500 - Serverfeil"
                    else -> "Ukjent feil"
                }

                callback(apiSuccess, errorString, listOf())
            }
        )

        requestQueue.add(messagesRequest)
    }

    fun sendMessage(messageText: String, userId: String, callback: (Boolean, String) -> Unit) {
        val url = baseUrl + "sendMessage"

        val postRequest: StringRequest = object : StringRequest(
            Method.POST,
            url,
            { json ->
                callback(true, "")
            },
            { error ->
                val errorString = when (error.networkResponse.statusCode) {
                    400 -> "400 - Feil input"
                    401 -> "401 - Bruker har ikke tilgang"
                    500 -> "500 - Serverfeil"
                    else -> "Ukjent feil"
                }

                callback(false, errorString)
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()

                params["message"] = messageText
                params["userId"] = userId

                return params
            }
        }

        requestQueue.add(postRequest)
    }

    private fun saveUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAllUsers()

            userDao.addUser(user)
        }.start()
    }

    fun fetchUser(callback: (User) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userDao.getCurrentUser()
            callback(user)
        }
    }

}