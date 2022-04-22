package com.example.smalltalk.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.smalltalk.R


class ProfileFragment : Fragment() {
    private lateinit var buttonSignout: Button
    private lateinit var backButton: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignout = view.findViewById(R.id.settings_button_signout)
        backButton = view.findViewById(R.id.settings_back_button)

        setButtons()
    }

    private fun setButtons() {
        buttonSignout.setOnClickListener {
            //TODO Delete user in database, navigate back to login screen, and clear backstack.
        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}