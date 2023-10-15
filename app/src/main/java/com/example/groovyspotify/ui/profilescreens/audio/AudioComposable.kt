package com.example.groovyspotify.ui.profilescreens.audio

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.groovyspotify.model.spotifyapidata.playlist.Playlist
import com.example.groovyspotify.model.spotifyapidata.track.Album
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.google.gson.Gson
import font.helveticaFamily


@Composable
fun OneTimeLazyRow(items: List<Playlist>,featuredAudioViewModel: FeaturedAudioViewModel) {

    LazyRow() {
        itemsIndexed(items) { index, item ->
            PlaylistRow(playlist = item){
                featuredAudioViewModel.onPlaylistClick(item)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }


}


@Composable
fun AlbumRow(album: Album, onAlbumClick: ()->Unit) {
    Box(modifier = Modifier.padding(start = 18.dp)) {
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                       onAlbumClick.invoke()
            },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(

                model = album.images[0].url,
                contentDescription = "Album Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(4.dp)
                    .size(60.dp)
            )
            Column(modifier = Modifier
                .padding(8.dp),verticalArrangement = Arrangement.Center) {
                Text(
                    text = album.name,
                    fontSize = 16.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Album . " + album.artists[0].name,
                    fontSize = 12.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistRow(playlist: Playlist, onPlaylistClick:()->Unit) {
//    var clickable by remember {
//        mutableStateOf(false)
//    }
//    var jsonData by remember {
//        mutableStateOf("")
//    }
//    if (clickable) {
//        navController.navigate("ExoPlayerImpl/$jsonData")
//    }
    Column(
        modifier = Modifier
            .size(180.dp)
            .padding(4.dp)
            .clickable {
                onPlaylistClick.invoke()
//                clickable = !clickable
//                jsonData = Uri.encode(Gson().toJson(playlist))
//                navController.navigate("PlaylistSongs/$jsonData")
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = playlist.images[0].url,
            contentDescription = "Album Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .padding(4.dp)
        )
        Text(
            modifier = Modifier.basicMarquee(),
            text = playlist.name,
            fontSize = 16.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackRow(track: TrackResponse,trackClick: () -> Unit) {
//    var clickable by remember {
//        mutableStateOf(false)
//    }
//    var jsonData by remember {
//        mutableStateOf("")
//    }
//    if (clickable) {
//        navController.navigate("ExoPlayerImpl/$jsonData")
//    }
    Box(modifier = Modifier
        .padding(start = 18.dp)
        .clickable {
            trackClick.invoke()
//            clickable = !clickable
//            jsonData = Uri.encode(Gson().toJson(track))
//            Log.d("clickedTrack", "TrackRow: $jsonData")

        }) {
        Row(modifier =
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(60.dp)
            ,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = track.album.images[0].url,
                contentDescription = "Album Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(4.dp)
                .size(60.dp)
            )
            Column(modifier = Modifier
                .padding(8.dp),verticalArrangement = Arrangement.Center) {

                Text(

                    text = track.name,
                    fontSize = 16.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Song . " + track.artists[0].name,
                    fontSize = 12.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

