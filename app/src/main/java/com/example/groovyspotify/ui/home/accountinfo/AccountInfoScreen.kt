package com.example.groovyspotify.ui.home.accountinfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.common.composable.CommonButton
import com.example.groovyspotify.common.composable.CommonProgressSpinner
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.common.composable.ExtraBoldText
import com.example.groovyspotify.common.composable.UniversalButton
import com.example.groovyspotify.common.composable.displayToast
import com.example.groovyspotify.ui.auth.LoginViewModel
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation
//import com.example.groovyspotify.ui.exoplayer.NavEliminationViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountInfoScreen(loginViewModel: LoginViewModel,
                      openAndPopUpLoginScreen:(String,String) -> Unit,
                      openGenderAndDob:(String) -> Unit,
                      openSearch:(String)->Unit,
                      openLanguagesAndArtists:(String) -> Unit,
                      accountInfoViewModel: AccountInfoViewModel

                      ) {
    val toastMsgEvent by loginViewModel.poppy

    val userData by loginViewModel.userDataState
    val signedIn by loginViewModel.signedIn

    val randomSubset = userData.favArtists.chunked(5)
    val logout by loginViewModel.logoutInProgress
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val userGet by loginViewModel.userGetting
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                setMediaItem(
                    MediaItem.Builder()
                        .apply {
                            setUri(
                                userData.track.previewUrl
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





    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(scrollState)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {


            CommonText(text = "AccountInfo", modifier = Modifier)
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(700.dp)

                    .border(shape = RoundedCornerShape(20.dp), color = Color.White, width = 2.dp)


            ) {
                AsyncImage(
                    model = userData.profilePhotoUrl,
                    contentDescription = "My Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(shape = RoundedCornerShape(20.dp), color = Color.White),
                    alignment = Alignment.Center
                )
                Column(modifier = Modifier.align(Alignment.BottomCenter), horizontalAlignment = Alignment.Start) {
                    ExtraBoldText(text = userData.name + " ${userData.age}", modifier = Modifier)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)

                            .padding(8.dp)
                            .background(color = Color.DarkGray.copy(0.8f)),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Card(
                            modifier = Modifier
                                .size(128.dp)
                                .padding(horizontal = 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(2.dp, Color.White),
                            elevation = 8.dp,
                            backgroundColor = Color.Gray,

                            ) {

                            AsyncImage(
                                model = userData.track.images[0].url,

                                contentDescription = "My album art",
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )
                            IconButton(

                                onClick = {
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


                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {


                            CommonText(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .basicMarquee(),
                                text = userData.track.name,

                                )


                            CommonText(
                                modifier = Modifier.padding(2.dp),
                                text = userData.track.artists[0].name

                            )

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
            }
            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                backgroundColor = Color.DarkGray,
                shape = RoundedCornerShape(20.dp)

            ) {
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 8.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        ExtraBoldText(
                            text = "Languages", modifier = Modifier
                                .padding(start = 10.dp, top = 8.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "edit",
                            modifier = Modifier.clickable {
                                accountInfoViewModel.onLanguagesEditClick(openLanguagesAndArtists)
                            })
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        itemsIndexed(userData.favLanguages) { index, item ->

                            Button(
                                modifier = Modifier
                                    .wrapContentHeight(Alignment.CenterVertically)
                                    .padding(horizontal = 4.dp),
                                shape = RoundedCornerShape(24.dp),
                                onClick = {

                                },
                                colors = ButtonDefaults
                                    .buttonColors(
                                        Color.Black

                                    ),
                                enabled = false
                            ) {

                                CommonText(text = item, modifier = Modifier)

                            }

                        }
                    }
                }


            }
            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(400.dp),
                backgroundColor = Color.DarkGray,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 8.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ExtraBoldText(
                            text = "Artists", modifier = Modifier

                        )
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "edit",
                            modifier = Modifier.clickable {
                                accountInfoViewModel.onArtistsEditClick(openLanguagesAndArtists)
                            })
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp)
                            .padding(bottom = 40.dp),

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        itemsIndexed(randomSubset) { index, items ->


                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(8.dp),

                                ) {

                                itemsIndexed(items) { index, item ->
                                    Button(
                                        modifier = Modifier
                                            .wrapContentHeight(Alignment.CenterVertically)
                                            .padding(horizontal = 4.dp),
                                        shape = RoundedCornerShape(24.dp),
                                        onClick = {

                                        },
                                        colors = ButtonDefaults
                                            .buttonColors(
                                                Color.Black

                                            ),
                                        enabled = false
                                    ) {

                                        CommonText(text = item.name, modifier = Modifier)

                                    }

                                }
                            }
                        }
                    }
                }


            }

            if (signedIn) {
                CommonButton(
                    text = "Logout",
                    modifier = Modifier.padding(20.dp),
                    color = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                    enabled = true
                ) {
                    loginViewModel.signout(
                        onSuccess = {
                            accountInfoViewModel.onLogoutClick(openAndPopUpLoginScreen)
                        },

                    )


                }
            }
        }
        toastMsgEvent?.let { displayToast(toastMsgEvent = it) }
        if(logout)
            CommonProgressSpinner()
        if (userGet)
            CommonProgressSpinner()
    }
}




//@Preview
//@Composable
//fun AccountINfoScreenPreview() {
//    AccountInfoDupe()
//}
//
//@Composable
//fun AccountInfoScreen(
//    viewModel: LoginViewModel?,
////    firestoreViewModel: FirestoreViewModel?,
////    navEliminationViewModel: NavEliminationViewModel?,
//    navController: NavController
//) {
//    val scope = rememberCoroutineScope()
//    val myUserProfile = firestoreViewModel?.myUserProfile?.collectAsState()
//////    val trackState = spotifyApiViewModel?.trackState!!.collectAsState()
////    var accessToken by remember{ mutableStateOf("") }
////    val accessTokenState = spotifyApiViewModel?.accessTokenResponse?.collectAsState()
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Color.Black
//
//            )
//    )
////
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(12.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        if (true
////            viewModel?.currentUser != null
//            ) {
//
//            myUserProfile?.value.let {
//                when (it) {
//                    is Resource.Failure -> {
//                        val context = LocalContext.current
//                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
//                    }
//
//                    Resource.Loading -> {
//                        CircularDotsAnimation()
//                    }
//
//                    is Resource.Success -> {
//                        ProfileCard(
//                            viewModel = viewModel,
//                            userProfile = it.data,
//                            navController = navController,
//                            isHomeScreen = false
//
//                        )
//                    }
//
//                    else -> {}
//                }
//
//
//            }
//        } else {
//
//            Box(modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black))
//            {
//                Button(
//                    onClick = {
//
//                        navController.navigate("LoginAuthScreen") {
//                            popUpTo("HomeScreen") { inclusive = true }
//                        }
//                    },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(64.dp)
//                        .align(Alignment.Center),
//                    shape = RoundedCornerShape(16.dp)
//                ) {
//                    Text(
//                        text = "Please login",
//                        fontSize = 18.sp,
//                        fontFamily = helveticaFamily,
//                        fontStyle = FontStyle.Normal,
//                        fontWeight = FontWeight.Medium
//                    )
//
//                }
//            }
//        }
////            PhotoUploadScreen()
////            ContactSyncScreen()
//
//
////            Column {
////                Button(onClick = {
//////                    spotifyApiViewModel.getTrackData(trackId = "11dFghVXANMlKmJXsNCbNl", authorization = "Bearer $accessToken")
////                }) {
////                    Text(
////                        text = "Click me to get Track data",
////                        fontSize = 28.sp,
////                        modifier = Modifier.fillMaxWidth().height(200.dp),
////                        color = Color.White
////                    )
////                }
////                trackState?.value.let {
////                    when (it) {
////                        is Resource.Failure -> {
////                            val context = LocalContext.current
////                            Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
////                        }
////
////                        Resource.Loading -> {
////                            CircularProgressIndicator()
////                        }
////
////                        is Resource.Success -> {
////                            Column() {
////
////
////                                Text(
////                                    text = it.data.album.name,
////                                    fontSize = 28.sp,
////                                    modifier = Modifier.weight(0.7f),
////                                    color = Color.White
////                                )
////                                Text(
////                                    text = it.data.id,
////                                    fontSize = 28.sp,
////                                    modifier = Modifier.weight(0.7f),
////                                    color = Color.White
////                                )
////                            }
////                        }
////
////                        else -> {}
////                    }
////                }
//    }
//}
//
//
//@Preview
//@Composable
//fun HomeScreenPreview() {
//
//    AccountInfoScreen(
//        null,
//        firestoreViewModel = null,
//        navEliminationViewModel = null,
//        rememberNavController()
//    )
//
//
//}