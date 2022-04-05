package com.example.smalltalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.Volley
import com.example.smalltalk.viewmodels.LoginViewModel
import com.example.smalltalk.R


class LoginFragment : Fragment() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var signInButton: Button
    private val model: LoginViewModel by viewModels()

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

        model.buildDatabase(requireContext())

        signInButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            model.checkApi(Volley.newRequestQueue(context), username, password) { user ->
                if (user != null) {
                    model.saveUser(user) {
                        /* requireActivity().supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            add<ChatFragment>(R.id.main_fragment_container)
                        } */

                        requireActivity().runOnUiThread {
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToChatFragment())
                        }
                    }
                } else {
                    //TODO Add toast or whatever
                }
            }
        }
    }

}