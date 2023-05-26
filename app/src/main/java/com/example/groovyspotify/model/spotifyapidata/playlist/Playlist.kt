package com.example.groovyspotify.model.spotifyapidata.playlist

import com.example.groovyspotify.model.spotifyapidata.search.Tracks
import com.example.groovyspotify.model.spotifyapidata.track.ExternalUrls
import com.example.groovyspotify.model.spotifyapidata.track.Image

data class Playlist(
    val collaborative: Boolean,
    val description: String,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val primary_color: Any,
    val `public`: Boolean,
    val snapshot_id: String,
    val tracks: Tracks,
    val type: String,
    val uri: String
)