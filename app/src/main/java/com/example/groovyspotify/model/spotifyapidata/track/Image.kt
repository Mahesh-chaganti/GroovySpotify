package com.example.groovyspotify.model.spotifyapidata.track

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val height: Int = 0,
    val url: String ="",
    val width: Int = 0
) : Parcelable