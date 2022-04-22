package com.example.smalltalk.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.Volley
import com.example.smalltalk.R


class LoginFragment : Fragment() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var signInButton: Button
    lateinit var progressBar: ProgressBar
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
        progressBar = view.findViewById(R.id.login_progressbar)

        model.buildDatabase(requireContext())

        setListeners()
    }

    private fun setListeners() {

        signInButton.setOnClickListener {
            model.pleaseWait.postValue(true)

            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            model.fetchUser(Volley.newRequestQueue(context), username, password)
        }

        model.activeUser.observe(viewLifecycleOwner) {
            requireActivity().runOnUiThread {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToChatFragment()
                )
            }
        }

        model.errorText.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        }

        model.pleaseWait.observe(viewLifecycleOwner) { pleaseWait ->
            when (pleaseWait) {
                true -> {
                    progressBar.visibility = View.VISIBLE
                    signInButton.isClickable = false
                    signInButton.text = "Logger inn..."
                }
                false -> {
                    progressBar.visibility = View.GONE
                    signInButton.isClickable = true
                    signInButton.text = "Logg inn"
                }
            }
        }
    }

}