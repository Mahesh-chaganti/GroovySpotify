package com.example.groovyspotify.model.spotifyapidata.searchTAP

import com.example.groovyspotify.model.spotifyapidata.search.Tracks

data class TrackAlbumPlaylist(
    val albums: Albums,
    val playlists: Playlists,
    val tracks: Tracks
)