package com.example.groovyspotify.model.spotifyapidata.albumwithtracks

import com.example.groovyspotify.model.spotifyapidata.track.Artist
import com.example.groovyspotify.model.spotifyapidata.track.ExternalUrls
import com.example.groovyspotify.model.spotifyapidata.track.Image

data class AlbumWithTracks(
    val album_type: String = "",
    val artists: List<Artist> = listOf(),
    val copyrights: List<Copyright> = listOf(),
    val external_ids: ExternalIds = ExternalIds(),
    val external_urls: ExternalUrls = ExternalUrls(),
    val genres: List<Any> = listOf(),
    val href: String = "",
    val id: String = "",
    val images: List<Image> = listOf(),
    val is_playable: Boolean = false,
    val label: String = "",
    val name: String = "",
    val popularity: Int = 0,
    val release_date: String = "",
    val release_date_precision: String = "",
    val total_tracks: Int = 0,
    val tracks: Tracks = Tracks(),
    val type: String = "",
    val uri: String = ""
)