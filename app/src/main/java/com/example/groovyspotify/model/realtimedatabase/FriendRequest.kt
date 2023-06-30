package com.example.groovyspotify.model.realtimedatabase

data class FriendRequest(
    val senderUsername: String = "",
    val receiverUsername: String = "",
    val status: String = "",
    val time: String = ""
)
