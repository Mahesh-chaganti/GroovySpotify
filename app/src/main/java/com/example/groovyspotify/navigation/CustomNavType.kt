package com.example.groovyspotify.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.spotifyapidata.playlist.Playlist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.google.gson.Gson

class UserProfileType : NavType<UserProfile>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UserProfile? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): UserProfile {
        return Gson().fromJson(value, UserProfile::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: UserProfile) {
        bundle.putParcelable(key, value)
    }
}
class TrackType : NavType<TrackResponse>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): TrackResponse? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): TrackResponse {
        return Gson().fromJson(value, TrackResponse::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: TrackResponse) {
        bundle.putParcelable(key, value)
    }
}
class PlaylistType : NavType<Playlist>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Playlist? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): Playlist {
        return Gson().fromJson(value, Playlist::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: Playlist) {
        bundle.putParcelable(key, value)
    }
}