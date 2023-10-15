package com.example.groovyspotify.model.firestore

import android.os.Parcelable
import com.example.groovyspotify.model.spotifyapidata.track.Artist
import com.example.groovyspotify.model.spotifyapidata.track.ExternalUrls
import com.example.groovyspotify.model.spotifyapidata.track.Image
import kotlinx.parcelize.Parcelize


data class TrackData(
    var album: String = "",
    var artists: List<Artist> = listOf(Artist()),
    var externalUrls: ExternalUrls = ExternalUrls(),
    var id: String = "",
    var images : List<Image> = listOf(Image()),
    var name: String = "",
    var previewUrl: String = "",
    var releaseDate: String = ""

)
