package com.example.groovyspotify.ui.profilescreens.audio

import com.example.groovyspotify.model.spotifyapidata.albumwithtracks.AlbumWithTracks
import com.example.groovyspotify.model.spotifyapidata.playlist.Playlist
import com.example.groovyspotify.model.spotifyapidata.searchTAP.TrackAlbumPlaylist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse

data class FeaturedAudioUiState(
    val searchQuery: String = "",
    var tapData: TrackAlbumPlaylist = TrackAlbumPlaylist(),
    var clickedTrack: TrackResponse = TrackResponse(),
    var clickedPlaylist: Playlist = Playlist(),
    var clickedAlbum: AlbumWithTracks = AlbumWithTracks(),
    var featuredTrack: TrackResponse = TrackResponse(),

)


