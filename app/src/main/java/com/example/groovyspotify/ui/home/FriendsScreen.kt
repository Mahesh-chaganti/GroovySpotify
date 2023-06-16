package com.example.groovyspotify.ui.home

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation
import com.example.groovyspotify.ui.profilescreens.ContactSyncScreen
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import font.helveticaFamily

@Composable
fun FriendsScreen(viewModel: AuthViewModel,firestoreViewModel: FirestoreViewModel) {
    ContactSyncScreen(viewModel = viewModel, firestoreViewModel = firestoreViewModel )
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Color.Black
//
//            )
//    )
//    {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Text(
//                modifier = Modifier.padding(16.dp),
//                text = "Friends",
//                fontSize = 36.sp,
//                fontFamily = helveticaFamily,
//                fontStyle = FontStyle.Normal,
//                fontWeight = FontWeight.ExtraBold,
//                color = Color.White
//            )
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(10) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(400.dp)
//                            .padding(4.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .size(64.dp)
//                                .padding(8.dp),
//                            verticalAlignment = Alignment.Top,
//                            horizontalArrangement = Arrangement.Start
//                        ) {
//                            AsyncImage(
//                                modifier = Modifier
//                                    .size(64.dp)
//                                    .background(shape = CircleShape, color = Color.Transparent)
//                                    .padding(8.dp),
//                                model = R.drawable.image,
//                                contentDescription = "user",
//                                contentScale = ContentScale.Crop,
//                                alignment = Alignment.Center,
//                            )
//                            Text(
//                                modifier = Modifier.padding(2.dp),
//                                text = "User $it is listening to",
//                                fontSize = 18.sp,
//                                fontFamily = helveticaFamily,
//                                fontStyle = FontStyle.Normal,
//                                fontWeight = FontWeight.ExtraBold,
//                                color = Color.White
//                            )
//                        }
//
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(8.dp)
//                                .border(
//                                    shape = RoundedCornerShape(16.dp),
//                                    width = 2.dp,
//                                    color = Color.White
//                                )
//                        ) {
//                            ExoplayerAnimation(
//                                isAnimating = true, modifier = Modifier
//                                    .padding(16.dp)
//                                    .align(
//                                        Alignment.BottomCenter
//                                    )
//                            )
//                        }
//
//                    }
//                }
//            }
//        }
//    }
}

@Preview
@Composable
fun FriendsScreenPreview() {
//    FriendsScreen(null,null)
}