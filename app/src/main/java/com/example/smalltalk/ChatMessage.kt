package com.example.smalltalk

import java.sql.Date

data class ChatMessage(val senderName: String, val timeSent: Date, val messageText: String)