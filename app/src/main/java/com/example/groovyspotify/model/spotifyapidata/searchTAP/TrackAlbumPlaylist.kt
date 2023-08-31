package com.example.groovyspotify.model.spotifyapidata.searchTAP

import com.example.groovyspotify.model.spotifyapidata.search.Tracks

data class TrackAlbumPlaylist(
    val albums: Albums = Albums(),
    val playlists: Playlists = Playlists(),
    val tracks: Tracks = Tracks()
)