package com.example.groovyspotify.model.spotifyapidata.playlist

import android.os.Parcelable
import com.example.groovyspotify.model.spotifyapidata.track.Album
import com.example.groovyspotify.model.spotifyapidata.track.Artist
import com.example.groovyspotify.model.spotifyapidata.track.ExternalIds
import com.example.groovyspotify.model.spotifyapidata.track.ExternalUrls
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val album: Album = Album(),
    val artists: List<Artist> = listOf(),
    val available_markets: List<String> = listOf(),
    val disc_number: Int= 0,
    val duration_ms: Int = 0,
    val episode: Boolean = false,
    val explicit: Boolean = false,
    val external_ids: ExternalIds = ExternalIds(),
    val external_urls: ExternalUrls = ExternalUrls(),
    val href: String = "",
    val id: String = "",
    val is_local: Boolean = false,
    val name: String = "",
    val popularity: Int = 0,
    val preview_url: String = "",
    val track: Boolean = false,
    val track_number: Int = 0,
    val type: String = "",
    val uri: String= ""
) : Parcelable