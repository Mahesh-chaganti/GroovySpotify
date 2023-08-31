package com.example.groovyspotify.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.groovyspotify.ui.auth.LoginViewModel
import com.example.groovyspotify.ui.fcm.FCMViewModel
import com.example.groovyspotify.ui.profilescreens.ContactSyncScreen
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.realtimedatabase.RealtimeDatabaseViewModel

@Composable
fun FriendsScreen(viewModel: LoginViewModel, firestoreViewModel: FirestoreViewModel, fcmViewModel: FCMViewModel, realtimeDatabaseViewModel: RealtimeDatabaseViewModel) {
    ContactSyncScreen(viewModel = viewModel, firestoreViewModel = firestoreViewModel,fcmViewModel, realtimeDatabaseViewModel )
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