package com.example.groovyspotify.model.spotifyapidata.searchTAP

import com.example.groovyspotify.model.spotifyapidata.playlist.Playlist

data class Playlists(
    val href: String = "",
    val items: List<Playlist> = listOf(),
    val limit: Int = 0,
    val next: String = "",
    val offset: Int = 0,
    val previous: Any = 0,
    val total: Int = 0
)