package com.example.groovyspotify.model.firestore

data class UserProfile(
    val name: String,
    val username: String,
    val email: String,
    val profilePhotoUrl: String,
    val mySongLink: String,
    val favoriteArtist: String,
    val favoriteSong: String
)
