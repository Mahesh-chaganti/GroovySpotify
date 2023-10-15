package com.example.groovyspotify.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.common.composable.ColoredText
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.common.composable.ExtraBoldText
import com.example.groovyspotify.ui.auth.LoginViewModel
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation
import com.example.groovyspotify.ui.fcm.FCMViewModel
import com.example.groovyspotify.ui.home.accountinfo.AccountInfoViewModel
import com.example.groovyspotify.ui.profilescreens.ContactSyncScreen
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.realtimedatabase.RealtimeDatabaseViewModel



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(accountInfoViewModel: AccountInfoViewModel,openSearch:(String) -> Unit) {


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
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {


                ExtraBoldText(text = "Feed", modifier = Modifier)
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        accountInfoViewModel.onSearchClick(openSearch)
                    }
                )
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    ExtraBoldText(text = "Spreadbeats' pick", modifier =Modifier.padding(start = 20.dp, top = 20.dp) )
                    Card(modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp)
                        .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = Color.DarkGray)
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),

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

                                Image(
                                    painter = painterResource(id = R.drawable.barker),

                                    contentDescription = "My album art",
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center
                                )
                                IconButton(

                                    onClick = {

//                            onPauseToggle.invoke()
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp),
                                        painter =
                                        if (true) {
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
                                    modifier = Modifier.padding(2.dp),
                                    text = "Track name"
//                        matchProfile.track.name?:"",

                                )


                                CommonText(
                                    modifier = Modifier.padding(2.dp),
                                    text = "Artist name"
//                        matchProfile.track.artists[0].name?:"",

                                )
                            }
                        }
                    }
                }



                items(10) {
                    Card(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .height(400.dp),

                        backgroundColor = Color.Black
                    ) {
                        Column {


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(64.dp),

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Card(modifier = Modifier
                                    .padding(4.dp)
                                    .size(40.dp),
                                    shape = CircleShape) {


                                    Image(
                                        painter = painterResource(id = R.drawable.richard),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(80.dp)),



                                        contentDescription = "user",

                                        alignment = Alignment.Center,
                                    )
                                }
                                CommonText(
                                    modifier = Modifier.padding(2.dp),
                                    text = "User $it is listening to",

                                    )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .border(
                                        shape = RoundedCornerShape(16.dp),
                                        width = 2.dp,
                                        color = Color.White
                                    )
                            ) {
                                Column(
                                    Modifier
                                        .size(256.dp)
                                        .padding(top = 20.dp)
                                        .align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box {


                                        Image(
                                            painter = painterResource(id = R.drawable.richard),
                                            contentDescription = "Album Cover",
                                            modifier = Modifier
                                                .size(128.dp)
                                                .padding(bottom = 8.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                        IconButton(

                                            onClick = {

//                            onPauseToggle.invoke()
                                            },
                                            modifier = Modifier.align(Alignment.Center)

                                        ) {
                                            Icon(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .align(Alignment.Center),
                                                painter =
                                                if (true) {
                                                    painterResource(id = R.drawable.round_pause_24)
                                                } else {
                                                    painterResource(id = R.drawable.round_play_arrow_24)
                                                },
                                                contentDescription = "Play/Pause",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                    CommonText(
                                        text = "Track name",
//                                        uiState.clickedTrack.name,
                                        modifier = Modifier
                                            .basicMarquee()
                                            .padding(2.dp)
                                    )
                                    ColoredText(
                                        text = "Artist name",
//                                        uiState.clickedTrack.artists[0]?.name ?: "",
                                        color = Color.Gray, modifier = Modifier
                                            .basicMarquee()
                                            .padding(2.dp)
                                    ) {

                                    }
                                }

                                ExoplayerAnimation(
                                    isAnimating = true, modifier = Modifier
                                        .padding(start = 8.dp, bottom = 20.dp)
                                        .padding(16.dp)
                                        .align(
                                            Alignment.BottomCenter
                                        )
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun FriendsScreenPreview() {
//    FeedScreenDupe()
////    FriendsScreen(null,null)
//}