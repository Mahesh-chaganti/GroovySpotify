package com.example.groovyspotify.model.spotifyapidata.track

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrls(
    val spotify: String = ""
) : Parcelable