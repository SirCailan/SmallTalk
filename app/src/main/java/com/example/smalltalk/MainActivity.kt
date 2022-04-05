package com.example.smalltalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    var backPressTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<LoginFragment>(R.id.main_fragment_container)
        } */
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        val backStackEntryCount = navHostFragment?.childFragmentManager?.backStackEntryCount

        if (backStackEntryCount != null && backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            val backMessage =
                Toast.makeText(this, "Trykk tilbake igjen for å lukke appen", Toast.LENGTH_LONG)

            if (backPressTime + 2000 > System.currentTimeMillis()) {
                backMessage.cancel()
                super.onBackPressed()
                return
            } else {
                backMessage.show()
            }

            backPressTime = System.currentTimeMillis()
        }
    }
}