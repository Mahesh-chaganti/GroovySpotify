package com.example.groovyspotify.model.spotifyapidata.playlist

import android.os.Parcelable
import com.example.groovyspotify.model.spotifyapidata.track.ExternalUrls
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val display_name: String = "",
    val external_urls: ExternalUrls = ExternalUrls(),
    val href: String = "",
    val id: String = "",
    val type: String = "",
    val uri: String=""
) : Parcelable