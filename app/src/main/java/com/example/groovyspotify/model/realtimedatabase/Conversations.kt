package com.example.groovyspotify.model.realtimedatabase

data class Conversations(
    val conversationId: String = "",
    val messages: List<Message> = listOf(Message()),
    val users: List<User> = listOf(User())
)
