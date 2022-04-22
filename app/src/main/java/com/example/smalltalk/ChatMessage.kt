package com.example.smalltalk

data class ChatMessage(
    val userId: String,
    val message: String,
    val userName: String,
    val timestamp: Long
)