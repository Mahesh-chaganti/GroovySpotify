package com.example.groovyspotify.ui.profilescreens.audio

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.common.composable.ColoredText
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation
import font.helveticaFamily

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlaylistSongs(
    featuredAudioViewModel: FeaturedAudioViewModel,
    openAndPopUp: (String,String) -> Unit
) {
    val uiState by featuredAudioViewModel.uiState
    val context = LocalContext.current

    /* Convert our Image Resource into a Bitmap */
    val bitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.image)
    }

    /* Create the Palette, pass the bitmap to it */
    val palette = remember {
        Palette.from(bitmap).generate()
    }

    /* Get the dark vibrant swatch */
    val darkVibrantSwatch = palette.darkVibrantSwatch
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(colors = listOf(darkVibrantSwatch?.let { Color(it.rgb).copy(0.7f) } ?: Color.White,Color.Black))
//
        )
    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            AsyncImage(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 40.dp)
                    .size(200.dp),
                model = uiState.clickedPlaylist.images[0],
                contentScale = ContentScale.Crop,

                contentDescription = "PlaylistImage"
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

            ) {

                itemsIndexed(uiState.clickedPlaylist.tracks.items) { index,item ->

                    TrackRow(track = item,trackClick = {
                        featuredAudioViewModel.onTrackClick(item)

                    },
                    )
//
                }
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
                                                uiState.clickedTrack.preview_url
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
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AlbumSongs(
    featuredAudioViewModel: FeaturedAudioViewModel,
    openAndPopUp: (String, String) -> Unit
) {
    val uiState by featuredAudioViewModel.uiState
    val context = LocalContext.current

    /* Convert our Image Resource into a Bitmap */
    val bitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.image)
    }

    /* Create the Palette, pass the bitmap to it */
    val palette = remember {
        Palette.from(bitmap).generate()
    }

    /* Get the dark vibrant swatch */
    val darkVibrantSwatch = palette.darkVibrantSwatch
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(colors = listOf(darkVibrantSwatch?.let { Color(it.rgb).copy(0.7f) } ?: Color.White,Color.Black))
//
        )
    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            AsyncImage(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 40.dp)
                    .size(200.dp)

                ,
                model = uiState.clickedAlbum.images[0],
                contentScale = ContentScale.Crop,

                contentDescription = "PlaylistImage"
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

            ) {

                itemsIndexed(uiState.clickedAlbum.tracks.items) { index, item ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(60.dp)
                        ,
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                           model = uiState.clickedAlbum.images[0],
                            contentDescription = "Album Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(60.dp)

                        )
                        Column(modifier = Modifier
                            .padding(8.dp),verticalArrangement = Arrangement.Center) {

                            Text(

                                text = item.name,
                                fontSize = 16.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = item.artists[0].name,
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
                                            uiState.clickedTrack.preview_url
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
    }
}
//@Preview
//@Composable
//fun SongsSCreenPreview() {
//    PlaylistSongs()
//}


