package com.example.groovyspotify.model.realtimedatabase

data class User(
    val userName: String = "",
    val phone: String = "",
    val fcmToken: String = "",
    val friends: List<User> = listOf(User())
)
