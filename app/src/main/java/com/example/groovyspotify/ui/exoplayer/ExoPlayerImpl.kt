package com.example.groovyspotify.ui.exoplayer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import font.helveticaFamily


@Composable
fun ExoplayerImpl(navEliminationViewModel: NavEliminationViewModel, navController: NavController) {
    val context = LocalContext.current
    val myData = navEliminationViewModel.myData
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                setMediaItem(
                    MediaItem.Builder()
                        .apply {
                            setUri(
                                myData!!.preview_url
                            )
                            setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setDisplayTitle(myData!!.name)
                                    .build()
                            )
                        }
                        .build()
                )
                prepare()
                playWhenReady = true
            }
    }
    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }

    Box(modifier = Modifier.fillMaxSize()) {

        DisposableEffect(key1 = Unit) {
            val listener =
                object : Player.Listener {
                    override fun onEvents(
                        player: Player,
                        events: Player.Events
                    ) {
                        super.onEvents(player, events)

                        isPlaying = player.isPlaying
                        playbackState = player.playbackState
                    }
                }

            exoPlayer.addListener(listener)

            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }

        AndroidView(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false

                }
            }

        )
    }



    PlayerControls(
        modifier = Modifier.fillMaxSize(),
        isPlaying = { isPlaying },
        onPauseToggle = {
            when {
                exoPlayer.isPlaying -> {
                    // pause the video
                    exoPlayer.pause()


                }

                exoPlayer.isPlaying.not() &&
                        playbackState == Player.STATE_ENDED -> {
                    exoPlayer.seekTo(0)
                    exoPlayer.playWhenReady = true
                }

                else -> {
                    // play the video
                    // it's already paused
                    exoPlayer.play()

                }
            }
            isPlaying = isPlaying.not()
        },
        title = { exoPlayer.mediaMetadata.displayTitle.toString() },
        playbackState = { playbackState },
        track = myData!!
    )


}


@Composable
fun CenterControls(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    playbackState: () -> Int,
    onPauseToggle: () -> Unit,
    title: () -> String,
    track: TrackResponse?
) {
    var isVideoPlaying = remember(isPlaying()) { isPlaying() }

    var title = remember(title()) { title() }
    var playerState by remember { mutableStateOf(playbackState()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier.align(Alignment.TopCenter).padding(top = 160.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {



            AsyncImage(
                model = track!!.album.images[0].url,
                contentDescription = "Album Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )
            Text(
                modifier = Modifier,
                text = track!!.name,
                fontSize = 24.sp,
                fontFamily = helveticaFamily,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,

                )
        }
        ExoplayerAnimation(
            isAnimating = isVideoPlaying,
            modifier = Modifier.align(Alignment.Center)
        )
        Card(
            modifier = Modifier
                .size(48.dp)
                .fillMaxSize()
                .align(Alignment.Center),
            shape = CircleShape,
            backgroundColor = Color(0xFFFF5722),
            contentColor = Color.White,
            border = BorderStroke(2.dp, Color.White)
        ) {
            IconButton(

                onClick = onPauseToggle
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    painter =
                    if (isVideoPlaying) {
                        painterResource(id = R.drawable.round_pause_24)
                    } else {
                        painterResource(id = R.drawable.round_play_arrow_24)
                    },
                    contentDescription = "Play/Pause",
                    tint = Color.White
                )
            }
        }

    }
}


@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,

    isPlaying: () -> Boolean,
    title: () -> String,
    playbackState: () -> Int,
    onPauseToggle: () -> Unit,
    track: TrackResponse
) {
    //black overlay across the video player


    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {


        CenterControls(
            modifier = Modifier.fillMaxSize(),

            isPlaying = isPlaying,

            onPauseToggle = onPauseToggle,
            playbackState = playbackState,
            title = title,
            track = track
        )


    }
}

@Preview
@Composable
fun CentralControlsPreview() {
    CenterControls(
        isPlaying = { true },
        playbackState = { 0 },
        onPauseToggle = { },
        title = { "" },
        track = null
    )
}
