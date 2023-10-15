package com.example.groovyspotify.ui.profilescreens.audio


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.groovyspotify.common.composable.BasicFieldComposable
import com.example.groovyspotify.common.composable.ColoredText
import com.example.groovyspotify.common.composable.CommonProgressSpinner
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.common.composable.ExtraBoldText
import com.example.groovyspotify.common.composable.displayToast
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation
import androidx.compose.material3.AlertDialog



import com.example.groovyspotify.R.string as AppText


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileFeaturedAudio(

    featuredAudioViewModel: FeaturedAudioViewModel,
    openScreen: (String) -> Unit,
    openAndPopUp:(String,String) -> Unit

) {
    val uiState by featuredAudioViewModel.uiState
    val toastMsgEvent by featuredAudioViewModel.poppy
    val loading by featuredAudioViewModel.inProgress

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black

            )
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .align(Alignment.TopStart)
        ) {
            ExtraBoldText(
            text = stringResource(id = AppText.featured_audio),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, top = 8.dp)

            )

            BasicFieldComposable(
                text = AppText.search,
                value = uiState.searchQuery,
                onNewValue = featuredAudioViewModel::onSearchValueChange,
                modifier = Modifier,
            )


            Spacer(modifier = Modifier.height(8.dp))
           LazyColumn() {
                itemsIndexed(uiState.tapData.tracks.items) { index, item ->
                    TrackRow(track = item,trackClick = {
                        featuredAudioViewModel.onTrackClick(item)

                    },
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
                itemsIndexed(uiState.tapData.albums.items) { index, item ->
                    Column() {
                        Spacer(modifier = Modifier.height(8.dp))

                        AlbumRow(album = item){
                            featuredAudioViewModel.getAlbumData(item.id)
                            openScreen("AlbumSongs")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        if (index == uiState.tapData.albums.items.size - 1) {
                            LazyRow() {
                                itemsIndexed(uiState.tapData.playlists.items) { index, item ->
                                    PlaylistRow(playlist = item){

                                        featuredAudioViewModel.onPlaylistClick(item)
                                        openScreen("PlaylistSongs")
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                        }

                    }
                }
            }
            toastMsgEvent?.let { displayToast(toastMsgEvent = it) }
        }
        val openDialog by featuredAudioViewModel.openDialog
//    openDialog1 = openDialog
        Log.d("Dialog screen", "ExoplayerDialog: $openDialog")
        if (openDialog) {
           AlertDialog(
                onDismissRequest = {
                    // release the exoplayer
                    featuredAudioViewModel.onDismissClick()
                },
                modifier = Modifier
                    .size(300.dp)
                    .background(color = Color.DarkGray)
                    .border(shape = RoundedCornerShape(12.dp), width = 1.dp, color = Color.White)
            ) {

                // Initialize the player when the dialog is first shown
                val context = LocalContext.current
                val exoPlayer = remember {
                    ExoPlayer.Builder(context)
                        .build()
                        .apply {
                            setMediaItem(
                                MediaItem.Builder()
                                    .apply {
                                        setUri(
                                            uiState.clickedTrack?.preview_url?:""
                                        )

                                    }
                                    .build()
                            )
                            prepare()
                            play()
                        }
                }


                // Release the player when the dialog is dismissed
                DisposableEffect(Unit) {
                    onDispose {
                        exoPlayer?.stop()
                        exoPlayer?.release()
                    }
                }
//            ExoPlayerInside(uiState.clickedTrack)

                Column(
                    Modifier
                        .size(256.dp)
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = uiState.clickedTrack.album?.images?.get(0)?.url ?: "",
                        contentDescription = "Album Cover",
                        modifier = Modifier
                            .size(128.dp)
                            .padding(bottom = 8.dp),
                        contentScale = ContentScale.Crop
                    )
                    Log.d("ExoName", "ExoplayerDialog: ${uiState.clickedTrack.name}")
                    CommonText(
                        text = uiState.clickedTrack.name, modifier = Modifier
                            .basicMarquee()
                            .padding(2.dp)
                    )
                    ColoredText(
                        text =
                        uiState.clickedTrack.artists[0]?.name ?: "",
                        color = Color.Gray, modifier = Modifier
                            .basicMarquee()
                            .padding(2.dp)
                    ) {

                    }
                    ExoplayerAnimation(
                        isAnimating = true,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 10.dp, bottom = 8.dp, top = 12.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End
                    ) {
                        ColoredText(
                            text = "Dismiss", modifier = Modifier.padding(10.dp),
                            color = Color(0xFFFF5722)
                        ) {
                            //release the exoplayer
                            featuredAudioViewModel.onDismissClick()
                        }
                        ColoredText(
                            text = "Confirm", modifier = Modifier.padding(10.dp),
                            color = Color(0xFFFF5722)
                        ) {
                            //call the set track function
                            featuredAudioViewModel.onSetTrack(trackResponse = uiState.clickedTrack)
                            featuredAudioViewModel.onConfirmClick(openAndPopUp)
                        }

                    }

                }
            }

        }
        if(loading)
            CommonProgressSpinner()
    }
}


//@Preview
//@Composable
//fun ProfileFeaturedAudioPreview() {
//    ProfileFeaturedAudio(
////        spotifyApiViewModel = null,
////        navEliminationViewModel = null,
////        navController = rememberNavController()
//    )
//}