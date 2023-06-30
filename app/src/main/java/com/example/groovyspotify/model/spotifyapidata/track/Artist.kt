package com.example.groovyspotify.model.spotifyapidata.track

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Artist(
    val external_urls: ExternalUrls = ExternalUrls(""),
    val href: String = "",
    val id: String= "",
    val name: String= "",
    val type: String= "",
    val uri: String= ""
) : Parcelable