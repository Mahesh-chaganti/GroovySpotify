package com.example.groovyspotify.model.firestore

import android.media.Image
import android.net.Uri
import android.provider.MediaStore.Audio.Media

data class UserProfile(
    val name: String?,
    val phone: String?,
    val userName: String?,
    val email: String?,
    val profilePhotoUrl: String?,
    val myLanguages: List<String?>?,
    val favoriteArtists: List<String?>?,
    val featuredAudio: String?
)
