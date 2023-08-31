package com.example.groovyspotify.ui.profilescreens.audio

import com.example.groovyspotify.model.spotifyapidata.searchTAP.TrackAlbumPlaylist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse

data class FeaturedAudioUiState(
    val searchQuery: String = "",
    var tapData: TrackAlbumPlaylist = TrackAlbumPlaylist(),
    var clickedTrack: TrackResponse = TrackResponse(),
    var featuredTrack: TrackResponse = TrackResponse(),

)


