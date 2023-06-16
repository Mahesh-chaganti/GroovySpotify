package com.example.groovyspotify.model.firestore

import android.media.Image
import android.net.Uri
import android.provider.MediaStore.Audio.Media

data class UserProfile(

    val name: String = "",
    val phone: String = "",
    val userName: String = "",
    val email: String = "",
    val profilePhotoUrl: String = "",
    var myLanguages: List<String> = listOf(),
    val favoriteArtists: List<String> = listOf(),
    val featuredAudio: String = ""
)
