package com.example.groovyspotify.model.spotifyapidata.albumwithtracks

import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse

data class Tracks(
    val href: String = "",
    val items: List<AlbumTrack> = listOf(),
    val limit: Int = 0,
    val next: String = "",
    val offset: Int = 0,
    val previous: String = "",
    val total: Int = 0
)