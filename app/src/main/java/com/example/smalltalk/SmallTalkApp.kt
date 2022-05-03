package com.example.smalltalk

import android.app.Application

class SmallTalkApp : Application(){
    override fun onCreate() {
        super.onCreate()

        application = this
    }

    companion object {
        lateinit var application: Application
    }
}