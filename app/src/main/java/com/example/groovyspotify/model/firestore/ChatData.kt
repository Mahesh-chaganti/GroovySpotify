package com.example.groovyspotify.model.firestore

data class ChatData(
    var chatId: String = "",
    var user1: ChatUser = ChatUser(),
    var user2: ChatUser = ChatUser()
)

