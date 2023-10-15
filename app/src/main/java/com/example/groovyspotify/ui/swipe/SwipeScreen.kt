package com.example.groovyspotify.ui.swipe

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.AsyncImage
import com.example.groovyspotify.common.composable.CommonProgressSpinner
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.ui.auth.LoginViewModel
import com.example.groovyspotify.ui.home.ProfilesScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun SwipeScreen(swipeScreenViewModel: SwipeScreenViewModel, loginViewModel: LoginViewModel) {

    val matchProfiles by loginViewModel.userProfilesStateFlow.collectAsState()
    val inProgress by loginViewModel.profilesInProgress
    val context = LocalContext.current



    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        // Spacer
        Spacer(modifier = Modifier.height(1.dp))

        // Cards
        val states = matchProfiles.map { it to rememberSwipeableCardState() }
        Box(
            modifier = Modifier
                .padding(24.dp)
                .size(600.dp)

        ) {
            Log.d("ProfilesInSwipeSCreen", "SwipeScreen: $matchProfiles")
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "No more profiles available")
                }
            states.forEach { (matchProfile, state) ->
                val exoPlayer = remember {
                    ExoPlayer.Builder(context)
                        .build()
                        .apply {
                            setMediaItem(
                                MediaItem.Builder()
                                    .apply {
                                        setUri(
                                            matchProfile.track.previewUrl
                                        )

                                    }
                                    .build()
                            )
                            prepare()
                            playWhenReady = false
                        }
                }
                var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

                // Release the player when the dialog is dismissed
                DisposableEffect(Unit) {
                    onDispose {
                        exoPlayer?.stop()
                        exoPlayer?.release()
                    }
                }
                ProfilesScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .swipeableCard(
                            state = state,
                            blockedDirections = listOf(Direction.Down),
                            onSwiped = {},
                            onSwipeCancel = { Log.d("Swipeable card", "Cancelled swipe") }),
                    matchProfile = matchProfile,
                    onPauseToggle = {
                        when {
                            exoPlayer.isPlaying -> {
                                // pause the video
                                exoPlayer.pause()


                            }

                            exoPlayer.isPlaying.not() -> {
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
                    isPlaying = { isPlaying }


                )

                LaunchedEffect(matchProfile, state.swipedDirection) {
                    if (state.swipedDirection != null) {
                        if (state.swipedDirection == Direction.Left ||
                            state.swipedDirection == Direction.Down
                        ) {
                            swipeScreenViewModel.onDislike(matchProfile)
                        } else {
                            swipeScreenViewModel.onLike(matchProfile)
                        }
                    }
                }
            }
        }

        // Buttons
        val scope = rememberCoroutineScope()
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CircleButton(onClick = {
                scope.launch {
                    val last = states.reversed().firstOrNull {
                        it.second.offset.value == Offset(0f, 0f)
                    }?.second
                    last?.swipe(Direction.Left)
                }
            }, icon = Icons.Rounded.Close)
            CircleButton(onClick = {
                scope.launch {
                    val last = states.reversed().firstOrNull {
                        it.second.offset.value == Offset(0f, 0f)
                    }?.second
                    last?.swipe(Direction.Right)
                }
            }, icon = Icons.Rounded.Favorite)
        }
        if (inProgress)
            CommonProgressSpinner()
        // Bottom nav bar
//            BottomNavigationMenu(
//                selectedItem = BottomNavigationItem.SWIPE,
//                navController = navController
//            )
    }


}


@Composable
private fun CircleButton(
    onClick: () -> Unit,
    icon: ImageVector,
) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary)
            .size(56.dp)
            .border(2.dp, MaterialTheme.colors.primary, CircleShape),
        onClick = onClick
    ) {
        Icon(
            icon, null,
            tint = MaterialTheme.colors.onPrimary
        )
    }
}


@Composable
private fun ProfileCard(
    modifier: Modifier,
    matchProfile: UserProfile,
) {
    Card(modifier) {
        Box {
            AsyncImage(
                model = matchProfile.profilePhotoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Scrim(Modifier.align(Alignment.BottomCenter))
            Column(Modifier.align(Alignment.BottomStart)) {
                CommonText(
                    text = matchProfile.name,

                    modifier = Modifier
                )
            }
        }
    }
}


//
//@Composable
//fun SwipeCards(swipeScreenViewModel: SwipeScreenViewModel) {
//    TransparentSystemBars()
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    listOf(
//                        Color(0xfff68084),
//                        Color(0xffa6c0fe),
//                    )
//                )
//            )
////                        .systemBarsPadding()
//    ) {
//        Box {
//            val states = profiles.reversed()
//                .map { it to rememberSwipeableCardState() }
//            var hint by remember {
//                mutableStateOf("Swipe a card or press a button below")
//            }
//
//            Hint(hint)
//
//            val scope = rememberCoroutineScope()
//            Box(
//                Modifier
//                    .padding(24.dp)
//                    .fillMaxSize()
//                    .aspectRatio(1f)
//                    .align(Alignment.Center)) {
//                states.forEach { (matchProfile, state) ->
//                    if (state.swipedDirection == null) {
//                        ProfileCard(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .swipeableCard(
//                                    state = state,
//                                    blockedDirections = listOf(Direction.Down),
//                                    onSwiped = {
//                                        // swipes are handled by the LaunchedEffect
//                                        // so that we track button clicks & swipes
//                                        // from the same place
//                                    },
//                                    onSwipeCancel = {
//                                        Log.d("Swipeable-Card", "Cancelled swipe")
//                                        hint = "You canceled the swipe"
//                                    }
//                                ),
//                            matchProfile = matchProfile
//                        )
//                    }
//                    LaunchedEffect(matchProfile, state.swipedDirection) {
//                        if (state.swipedDirection != null) {
//                            if (state.swipedDirection == Direction.Left ||
//                                state.swipedDirection == Direction.Down
//                            ) {
//                                swipeScreenViewModel.onDislike(matchProfile)
//                            } else {
//                                swipeScreenViewModel.onLike(matchProfile)
//                            }
//                            hint = "You swiped ${stringFrom(state.swipedDirection!!)}"
//                        }
//                    }
//                }
//            }
//            Row(
//                Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(horizontal = 24.dp, vertical = 32.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                CircleButton(
//                    onClick = {
//                        scope.launch {
//                            val last = states.reversed()
//                                .firstOrNull {
//                                    it.second.offset.value == Offset(0f, 0f)
//                                }?.second
//                            last?.swipe(Direction.Left)
//                        }
//                    },
//                    icon = Icons.Rounded.Close
//                )
//                CircleButton(
//                    onClick = {
//                        scope.launch {
//                            val last = states.reversed()
//                                .firstOrNull {
//                                    it.second.offset.value == Offset(0f, 0f)
//                                }?.second
//
//                            last?.swipe(Direction.Right)
//                        }
//                    },
//                    icon = Icons.Rounded.Favorite
//                )
//            }
//        }
//    }
//}
//@Composable
//private fun ProfileCard(
//    modifier: Modifier,
//    matchProfile: MatchProfile,
//) {
//    Card(modifier) {
//        Box {
//            Image(contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize(),
//                painter = painterResource(matchProfile.drawableResId),
//                contentDescription = null)
//            Scrim(Modifier.align(Alignment.BottomCenter))
//            Column(Modifier.align(Alignment.BottomStart)) {
//                Text(text = matchProfile.name,
//                    color = MaterialTheme.colors.onPrimary,
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier.padding(10.dp))
//            }
//        }
//    }
//}


@Composable
private fun Hint(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
            isNavigationBarContrastEnforced = false
        )
        onDispose {}
    }
}

private fun stringFrom(direction: Direction): String {
    return when (direction) {
        Direction.Left -> "Left 👈"
        Direction.Right -> "Right 👉"
        Direction.Up -> "Up 👆"
        Direction.Down -> "Down 👇"
    }
}


@Composable
fun Scrim(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            .height(180.dp)
            .fillMaxWidth()
    )
}

//@Preview()
//@Composable
//fun DefSwipeCardsPreview(){
//    SwipeCards()
//}