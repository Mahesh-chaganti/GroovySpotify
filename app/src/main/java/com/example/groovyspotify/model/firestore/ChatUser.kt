package com.example.groovyspotify.model.firestore

data class ChatUser(
    var userId: String = "",
    var name: String = "",
    var imageUrl: String = ""
)