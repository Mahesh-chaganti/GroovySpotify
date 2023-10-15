package com.example.groovyspotify.model.spotifyapidata.playlist

import android.os.Parcelable
import com.example.groovyspotify.model.spotifyapidata.search.Tracks
import com.example.groovyspotify.model.spotifyapidata.track.ExternalUrls
import com.example.groovyspotify.model.spotifyapidata.track.Image
import kotlinx.parcelize.Parcelize

data class Playlist(
    val collaborative: Boolean = false,
    val description: String = "",
    val external_urls: ExternalUrls = ExternalUrls(),
    val followers: Followers = Followers(),
    val href: String = "",
    val id: String = "",
    val images: List<Image> = listOf(),
    val name: String = "",
    val owner: Owner = Owner(),
    val primary_color: String = "",
    val `public`: Boolean = false,
    val snapshot_id: String = "",
    val tracks: Tracks = Tracks(),
    val type: String = "",
    val uri: String = ""
)