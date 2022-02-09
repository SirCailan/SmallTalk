package com.example.smalltalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ChatFragment>(R.id.main_fragment_container)
            }
    }
}