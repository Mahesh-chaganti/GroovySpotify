package com.example.groovyspotify.model.firestore

import android.media.Image
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore.Audio.Media
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

@Parcelize
data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val userName: String = "",
    val email: String = "",
    val profilePhotoUrl: String = "",
    var myLanguages: List<String> = listOf(),
    val favoriteArtists: List<String> = listOf(),
    val featuredAudio: String = "",
    val age: String,
    val gender: String,
    val genderPreferences: String,
    val swipesLeft:List<String> = listOf(),
    var swipesRight: List<String> = listOf(),
    var matches: List<String> = listOf()

): Parcelable
object COLLECTION_NAMES{
    const val COLLECTION_PROFILES = "UserProfiles"
    const val COLLECTION_MESSAGES = "Messages"
    const val COLLECTION_CHAT = "Chats"

}