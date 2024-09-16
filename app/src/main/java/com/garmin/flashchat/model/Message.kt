package com.garmin.flashchat.model

data class Message(
    val sender: String,
    val body: String,
    val date: Long = System.currentTimeMillis(),
    val isMeAvatar: Boolean = true
)
