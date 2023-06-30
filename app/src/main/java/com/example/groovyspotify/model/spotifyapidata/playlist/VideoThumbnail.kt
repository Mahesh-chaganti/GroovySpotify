package com.example.groovyspotify.model.spotifyapidata.playlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoThumbnail(
    val url: String = ""
) : Parcelable