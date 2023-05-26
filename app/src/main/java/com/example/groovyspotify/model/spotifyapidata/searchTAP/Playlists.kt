package com.example.groovyspotify.model.spotifyapidata.searchTAP

import com.example.groovyspotify.model.spotifyapidata.playlist.Playlist

data class Playlists(
    val href: String,
    val items: List<Playlist>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)