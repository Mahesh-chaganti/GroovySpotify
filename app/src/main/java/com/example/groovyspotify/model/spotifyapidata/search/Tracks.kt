package com.example.groovyspotify.model.spotifyapidata.search

import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse

data class Tracks(
    val href: String,
    val items: List<TrackResponse>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)