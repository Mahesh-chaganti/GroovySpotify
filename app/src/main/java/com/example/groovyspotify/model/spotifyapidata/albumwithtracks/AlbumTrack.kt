package com.example.groovyspotify.model.spotifyapidata.albumwithtracks

import com.example.groovyspotify.model.spotifyapidata.track.Artist
import com.example.groovyspotify.model.spotifyapidata.track.ExternalUrls

data class AlbumTrack(
    val artists: List<Artist> = listOf(),
    val disc_number: Int = 0,
    val duration_ms: Int = 0,
    val explicit: Boolean = false,
    val external_urls: ExternalUrls = ExternalUrls(),
    val href: String = "",
    val id: String = "",
    val is_local: Boolean = false,
    val is_playable: Boolean = false,
    val name: String = "",
    val preview_url: String ="",
    val track_number: Int =0,
    val type: String = "",
    val uri: String = ""
)