package com.example.smalltalk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.room.Room
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var signInButton: Button
    lateinit var userDao: UserDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameInput = view.findViewById(R.id.input_username)
        passwordInput = view.findViewById(R.id.input_password)
        signInButton = view.findViewById(R.id.button_sign_in)

        setButtons()

        val database =
            Room.databaseBuilder(requireContext(), AppDatabase::class.java, "SmallTalk_DATABASE")
                .build()

        userDao = database.userDao()

    }

    private fun setButtons() {
        signInButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username == "Cailan" && password == "passord123") {
                val currentUser =
                    User(userName = "Cailan", firstName = "Andreas", lastName = "Thomson")

                signIn(currentUser) {
                    requireActivity().supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        add<ChatFragment>(R.id.main_fragment_container)
                    }
                }
            }
        }
    }

    private fun signIn(currentUser: User, callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAllUsers()

            userDao.addUser(currentUser)

            callback()
        }.start()
    }
}