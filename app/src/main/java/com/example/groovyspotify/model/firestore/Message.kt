package com.example.groovyspotify.model.firestore


data class Message(
    val sentBy: String = "",
    val message: String = "",
    val timestamp: String = ""
)
