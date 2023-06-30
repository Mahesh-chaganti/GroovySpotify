package com.example.groovyspotify.model.realtimedatabase

data class Message(
    val messageId: String = "",
    val senderUsername: String = "",
    val receiverUsername: String = "",
    val actualMessage: String = "",
    val time: String = ""
)
