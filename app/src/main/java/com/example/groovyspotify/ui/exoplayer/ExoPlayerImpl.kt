package com.example.groovyspotify.ui.exoplayer

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import font.helveticaFamily
import kotlinx.coroutines.launch

import androidx.compose.material.AlertDialog
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerControlView
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.ui.home.CircularDotsAnimation
import com.example.groovyspotify.ui.profilescreens.audio.FeaturedAudioViewModel

import androidx.compose.runtime.*
import androidx.media3.common.MediaItem.fromUri

//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem.fromUri
//import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
//import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun ExoplayerImpl(featuredAudioViewModel: FeaturedAudioViewModel, navController: NavController) {
    val context = LocalContext.current
//    val myData = navEliminationViewModel.myData
    var track = featuredAudioViewModel.uiState.value.clickedTrack

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
                                    .setDisplayTitle(track!!.name)
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
            .align(Alignment.Center),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false

                }
            }

        )
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {


        CenterControls(
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
            playbackState = { playbackState },
            title = { exoPlayer.mediaMetadata.displayTitle.toString() },
            track = track,
            navController = navController,

            )


    }


}


@Composable
fun CenterControls(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    playbackState: () -> Int,
    onPauseToggle: () -> Unit,
    title: () -> String,
    track: TrackResponse?,
    navController: NavController?,

    ) {
    var userName by remember {
        mutableStateOf("")
    }
    var isVideoPlaying = remember(isPlaying()) { isPlaying() }

    var title = remember(title()) { title() }
    var playerState by remember { mutableStateOf(playbackState()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 160.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {


            AsyncImage(
                model = track?.album?.images?.get(0)?.url ?: "",
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val scope = rememberCoroutineScope()
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(40.dp),
                shape = RoundedCornerShape(24.dp),
                onClick = {
                    navController?.popBackStack()
                },
                colors = ButtonDefaults
                    .buttonColors(

                        backgroundColor = Color(0xFFFF3A20),
                        contentColor = Color.White


                    )
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium
                )

            }
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(24.dp),
                shape = RoundedCornerShape(24.dp),
                onClick = {
//                    featuredAudio = track?.preview_url!!
//                    val mapData = mapOf(
//                        "featuredAudio" to featuredAudio)
//                    scope.launch {
//                        navEliminationViewModel?.selectedFeaturedAudio(data = track)
//
////                        firestoreViewModel?.createOrUpdateMyUserProfile(
////                            userName = userName,
////                            mapData = mapData
////                        )
//                    }
//                    if(profilePhotoUrlEmpty) {
//                        navController?.navigate("PhotoUploadScreen")
//                    }
//                    else{
//                        navController?.navigate("MainHomeScreen")
//                    }
                },
                colors = ButtonDefaults
                    .buttonColors(

                        backgroundColor = Color(0xFFFF3A20),
                        contentColor = Color.White


                    )
            ) {
                Text(
                    text = "Set",
                    fontSize = 16.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium
                )

            }

        }
//        myUserProfile?.value.let {
//            when (it) {
//                is Resource.Failure -> {
//                    val context = LocalContext.current
//                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
//                }
//
//                Resource.Loading -> {
//                    CircularDotsAnimation()
//                }
//
//                is Resource.Success -> {
//                    userName = it.data.userName
//                    if(it.data.profilePhotoUrl.isNullOrBlank()){
//                       profilePhotoUrlEmpty = true
//                    }
//
//                }
//
//                else -> {}
//            }
//        }
    }

}


//@androidx.media3.common.util.UnstableApi
//@Preview
//@Composable
//fun PreviewPlayer() {
//    ExoplayerOutside()
//}

//@Preview
//@Composable
//fun CentralControlsPreview() {
//    CenterControls(
//        isPlaying = { true },
//        playbackState = { 0 },
//        onPauseToggle = { },
//        title = { "" },
//        track = null,
//        navController = null,
//
//    )
//}
