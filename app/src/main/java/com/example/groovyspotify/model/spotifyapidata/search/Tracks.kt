package com.example.groovyspotify.model.spotifyapidata.search

import android.os.Parcelable
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tracks(
    val href: String = "",
    val items: List<TrackResponse> = listOf(),
    val limit: Int = 0,
    val next: String = "",
    val offset: Int = 0,
    val previous: String = "",
    val total: Int = 0
) : Parcelable