package com.example.groovyspotify.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.groovyspotify.model.spotifyapidata.playlist.Playlist
import com.example.groovyspotify.model.spotifyapidata.track.Album
import com.example.groovyspotify.ui.profilescreens.audio.TrackRow

@Composable
fun PlaylistSongs(playlist: Playlist, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        AsyncImage(modifier = Modifier.size(128.dp).align(Alignment.TopCenter),model = playlist.images[0], contentDescription ="PlaylistImage" )
        LazyColumn(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {

            itemsIndexed(playlist.tracks.items) { index, item ->
                TrackRow(track = item, navController = navController){

                }
            }
        }
    }
}


@Composable
fun AlbumSongs(album: Album) {

}