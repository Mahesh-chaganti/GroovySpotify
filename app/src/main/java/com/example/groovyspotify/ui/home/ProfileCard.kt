package com.example.groovyspotify.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.profile.MusicLanguage
import com.example.groovyspotify.model.profile.ProfileArtist
import com.example.groovyspotify.model.profile.listOfMusicLanguages
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation
import com.example.groovyspotify.ui.exoplayer.NavEliminationViewModel
import com.example.groovyspotify.ui.profilescreens.ButtonArtist
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.profilescreens.UniversalButton
import font.helveticaFamily
import kotlin.math.log


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileCard(
    userProfile: UserProfile?,
    navController: NavController?,
    isHomeScreen : Boolean
    ) {

    val isHomeScreen by remember{ mutableStateOf(isHomeScreen) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Black)

    ) {



        AsyncImage(
            model = userProfile?.profilePhotoUrl, contentDescription = "My Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .graphicsLayer { alpha = 0.10f },
            alignment = Alignment.Center
        )
       Box(modifier = Modifier.fillMaxSize()) {
           if(isHomeScreen) {
               Icon(
                   modifier = Modifier
                       .align(Alignment.TopStart)
                       .padding(18.dp)
                       .size(24.dp)
                       .clickable { navController?.popBackStack() },
                   painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
                   contentDescription = "Back button",
                   tint = Color(0xFFFF5722)
               )
           }
           Text(
               modifier = Modifier.align(Alignment.TopCenter),
               text = "Profile",
               fontSize = 18.sp,
               fontFamily = helveticaFamily,
               fontStyle = FontStyle.Normal,
               fontWeight = FontWeight.Medium
           )
           Column(Modifier.fillMaxSize().align(Alignment.Center), verticalArrangement = Arrangement.SpaceEvenly) {


               ReUsableProfileCard(userProfile = userProfile, modifier = Modifier.fillMaxSize())



               if (userProfile != null) {
                   ArtistsAndLanguagesGrid(
                       userProfile = userProfile
                   )
               }
               if (navController != null && !isHomeScreen) {
                   Button(
                       onClick = {

                           navController?.navigate("LoginAuthScreen") {
                               popUpTo("HomeScreen") { inclusive = true }
                           }
                       },
                       colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(64.dp),
                       shape = RoundedCornerShape(16.dp)
                   ) {
                       Text(
                           text = "Logout",
                           fontSize = 18.sp,
                           fontFamily = helveticaFamily,
                           fontStyle = FontStyle.Normal,
                           fontWeight = FontWeight.Medium
                       )

                   }
               }
           }
       }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReUsableProfileCard(userProfile: UserProfile?, modifier: Modifier) {
    val context = LocalContext.current
    val randomSubset = userProfile?.favoriteArtists?.chunked(5)
    Log.d("Profile", "ProfileCard: $userProfile")
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                setMediaItem(
                    MediaItem.Builder()
                        .apply {
                            setUri(
                                userProfile?.featuredAudio
                            )
                            setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setDisplayTitle("")
                                    .build()
                            )
                        }
                        .build()
                )
                prepare()
                playWhenReady = false
            }
    }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying.not()) }
    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }
    AndroidView(modifier = Modifier
        .size(50.dp),
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false

            }
        }

    )
    Column(
        modifier = modifier

    ) {
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


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp)
                .padding(16.dp)
                .border(shape = RoundedCornerShape(8.dp), color = Color.White, width = 2.dp)


        ) {
            AsyncImage(
                model = userProfile?.profilePhotoUrl, contentDescription = "My Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .border(shape = RoundedCornerShape(8.dp), color = Color.Unspecified,width = 1.dp),
                alignment = Alignment.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                CardAlbumArt(
                    isAudioPlaying = { isPlaying },
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
                    }
                )


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {


                    userProfile?.name?.let {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = it,
                            fontSize = 24.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                    userProfile?.userName?.let {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = it,
                            fontSize = 24.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                    ExoplayerAnimation(
                        isAnimating = isPlaying,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(2.dp)
                    )
                }
            }


        }


//            if(!isHomeScreen){
//                Box(Modifier.fillMaxSize()) {
//                    Button(
//                        onClick = {
//
//                            navController?.navigate("LoginAuthScreen") {
//                                popUpTo("HomeScreen") { inclusive = true }
//                            }
//                        },
//                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(64.dp)
//                            .align(Alignment.Center),
//                        shape = RoundedCornerShape(16.dp)
//                    ) {
//                        Text(
//                            text = "Please login",
//                            fontSize = 18.sp,
//                            fontFamily = helveticaFamily,
//                            fontStyle = FontStyle.Normal,
//                            fontWeight = FontWeight.Medium
//                        )
//
//                    }
//                }
//            }
//            else{
//
//            }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtistsAndLanguagesGrid(userProfile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,

        ) {

        Text(
            modifier = Modifier.padding(2.dp),
            text = "Artists",
            fontSize = 36.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        LazyHorizontalStaggeredGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalItemSpacing = 8.dp,
            rows = StaggeredGridCells.Fixed(3)
        ) {

            itemsIndexed(userProfile?.favoriteArtists as List<String>) { index, item ->
                LangAndArtistButton(modifier = Modifier.wrapContentSize(), text = item)
            }
        }
        Text(
            modifier = Modifier.padding(2.dp),
            text = "Languages",
            fontSize = 36.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        LazyHorizontalStaggeredGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalItemSpacing = 8.dp,
            rows = StaggeredGridCells.Fixed(3)
        ) {

            itemsIndexed(userProfile?.myLanguages as List<String>) { index, item ->
                LangAndArtistButton(modifier = Modifier.wrapContentSize(), text = item)
            }
        }
    }
}

@Composable
fun LangAndArtistButton(modifier: Modifier, text: String) {


    Button(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        onClick = {


        },
        colors = ButtonDefaults
            .buttonColors(

                backgroundColor = Color.DarkGray,


                )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium
        )
    }

}


@Composable
fun CardAlbumArt(isAudioPlaying: () -> Boolean, onPauseToggle: () -> Unit) {
    val isPlaying = remember(isAudioPlaying()) { isAudioPlaying() }
    Card(
        modifier = Modifier
            .size(128.dp)
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color.White),
        elevation = 8.dp,
        backgroundColor = Color.Gray,

        ) {

        AsyncImage(
            model = R.drawable.image,
            contentDescription = "My album art",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        IconButton(

            onClick = {
                onPauseToggle.invoke()
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter =
                if (isPlaying) {
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

@Preview
@Composable
fun ProfileCardPreview() {
    ProfileCard(null, rememberNavController(),false)
}