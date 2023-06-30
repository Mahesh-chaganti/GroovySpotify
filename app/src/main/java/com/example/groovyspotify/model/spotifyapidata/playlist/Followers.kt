package com.example.groovyspotify.model.spotifyapidata.playlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Followers(
    val href: String = "",
    val total: Int = 0
) : Parcelable