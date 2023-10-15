package com.example.groovyspotify.model.firestore

import android.os.Parcelable
import com.example.groovyspotify.model.profile.ProfileArtist
import kotlinx.parcelize.Parcelize


data class UserProfile(
    var uid: String = "",
    var name: String = "",
    var phone: String = "",
    var userName: String = "",
    var email: String = "",
    var profilePhotoUrl: String = "",
    var favLanguages: List<String> = listOf(),
    var favArtists: List<ProfileArtist> = listOf(),
    var track: TrackData = TrackData(),
    var age:String = "",
    var gender: String = "",
    var swipesLeft:List<String> = listOf(),
    var swipesRight: List<String> = listOf(),
    var matches: List<String> = listOf()


){
    fun toMap() = mapOf(
        "uid" to uid,
        "name" to name,
        "phone" to phone,
        "email" to email,
        "userName" to userName,
        "profilePhotoUrl" to profilePhotoUrl,
        "gender" to gender,
        "favLanguages" to favLanguages,
        "favArtists" to favArtists,
        "track" to track,
        "age" to age,
        "swipesLeft" to swipesLeft,
        "swipesRight" to swipesRight,
        "matches" to matches
    )
}
object COLLECTION_NAMES{
    const val COLLECTION_PROFILES = "UserProfiles"
    const val COLLECTION_MESSAGES = "Messages"
    const val COLLECTION_CHAT = "Chats"

}