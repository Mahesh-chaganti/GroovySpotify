package com.example.groovyspotify.ui.profilescreens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.spotifyapidata.playlist.Playlist
import com.example.groovyspotify.model.spotifyapidata.track.Album
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.example.groovyspotify.ui.exoplayer.NavEliminationViewModel
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import font.helveticaFamily

@Composable
fun ProfileFeaturedAudio(spotifyApiViewModel: SpotifyApiViewModel?,navEliminationViewModel: NavEliminationViewModel?, navController: NavController) {

    var search by remember { mutableStateOf("") }
    val tapState = spotifyApiViewModel?.tapState!!.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black

            )
    )
    {
        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(18.dp)
                .size(24.dp)
                .clickable { navController.popBackStack() },
            painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
            contentDescription = "Back button",
            tint = Color(0xFFFF5722)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, top = 8.dp)
                .align(Alignment.TopStart),
            text = "Update your featured audio",
            fontSize = 36.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Column() {


            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(top = 96.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = {
                        Text(
                            text = "Search for a song",
                            fontSize = 18.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),

                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color.White,
                        textColor = Color.White,
                        backgroundColor = Color.DarkGray,
                        focusedBorderColor = Color(0xFF0890CD),
                        unfocusedBorderColor = Color.White,
                        disabledTextColor = Color.White,
                        focusedLabelColor = Color(0xFF0890CD),
                        placeholderColor = Color.White

                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }



            tapState?.value.let {
                when (it) {
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        Toast.makeText(
                            context,
                            it.exception.message + "TAP call",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                    Resource.Loading -> {
                        CircularProgressIndicator()
                    }

                    is Resource.Success -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {


                            LazyColumn() {
                                itemsIndexed(it.data.tracks.items) { index, item ->
                                    TrackRow(navEliminationViewModel = navEliminationViewModel!!,track = item, navController = navController)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                                itemsIndexed(it.data.albums.items) { index, item ->
                                    Column() {
                                        Spacer(modifier = Modifier.height(8.dp))

                                        AlbumRow(album = item)
                                        Spacer(modifier = Modifier.height(8.dp))
                                        if (index == it.data.albums.items.size - 1) {
                                            OneTimeLazyRow(items = it.data.playlists.items)
                                        }

                                    }
                                }


                            }

                        }
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun OneTimeLazyRow(items: List<Playlist>) {

    LazyRow() {
        itemsIndexed(items) { index, item ->
            PlaylistRow(playlist = item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }


}


@Composable
fun AlbumRow(album: Album) {
    Box(modifier = Modifier.padding(start = 18.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = album.images[0].url,
                contentDescription = "Album Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .padding(4.dp)
            )
            Column() {
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
fun PlaylistRow(playlist: Playlist) {
    Column(
        modifier = Modifier
            .size(180.dp)
            .padding(4.dp),
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
fun TrackRow(navEliminationViewModel: NavEliminationViewModel,track: TrackResponse, navController: NavController) {
    Box(modifier = Modifier.padding(start = 18.dp).clickable {
            navEliminationViewModel.setTrackData(data = track)
        navController.navigate("ExoPlayerImpl")
    }) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = track.album.images[0].url,
                contentDescription = "Album Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .padding(4.dp)
            )
            Column() {

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


@Preview
@Composable
fun ProfileFeaturedAudioPreview() {
    ProfileFeaturedAudio(spotifyApiViewModel = null,navEliminationViewModel = null, navController = rememberNavController())
}