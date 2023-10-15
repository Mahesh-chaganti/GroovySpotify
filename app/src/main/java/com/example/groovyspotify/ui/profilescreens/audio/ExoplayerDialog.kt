package com.example.groovyspotify.ui.profilescreens.audio

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation
import com.example.groovyspotify.R
import com.example.groovyspotify.common.composable.ColoredText
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.example.groovyspotify.ui.profilescreens.audio.FeaturedAudioViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExoplayerDialog(featuredAudioViewModel: FeaturedAudioViewModel) {
//    var openDialog1 by rememberSaveable {
//        mutableStateOf(false)
//    }

}

@Composable
fun ExoplayerDi(track: TrackResponse) {


}

@Composable
fun ExoplayerOutside(track: TrackResponse) {


    val trackURL = track.preview_url
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val exoPlayer = ExoPlayer.Builder(context)
        .build()
        .apply {
            setMediaItem(
                MediaItem.Builder()
                    .apply {
                        setUri(
                            track.preview_url
                        )
                        playWhenReady = true
                        prepare()
                    }.build()
            )
        }
    DisposableEffect(key1 =

    AndroidView(modifier = Modifier,
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
            }
        }),
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        Log.e("LIFECYCLE", "resumed")
                        exoPlayer.play()
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        Log.e("LIFECYCLE", "paused")
                        exoPlayer.stop()
                    }

                    else -> {}
                }
            }
            val lifecycle = lifecycleOwner.value.lifecycle
            lifecycle.addObserver(observer)
            onDispose {
                exoPlayer.release()
                lifecycle.removeObserver(observer)
            }
        }
    )

}

@Composable
fun ExoPlayerInside(track: TrackResponse) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                setMediaItem(
                    MediaItem.Builder()
                        .apply {
                            setUri(
                                track.preview_url
                            )
                            setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setDisplayTitle(track.name)
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

    Box(modifier = Modifier) {

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
            .align(Alignment.Center),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true

                }
            }

        )
    }
}


//@Preview
//@Composable
//fun ExoplayerDialogPreview() {
//    ExoplayerDialog()
//}