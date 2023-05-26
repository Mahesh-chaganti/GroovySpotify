package com.example.groovyspotify.model.firestore

import android.media.Image
import android.provider.MediaStore.Audio.Media

data class UserProfile(
    val name: String,
    val username: String,
    val email: String,
    val profilePhotoUrl: Image,
    val myLanguages: List<String>,
    val favoriteArtists: List<String>,
    val featuredAudio: Media
)
