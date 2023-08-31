package com.example.groovyspotify.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.ui.auth.LoginViewModel
//import com.example.groovyspotify.ui.exoplayer.NavEliminationViewModel
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import font.helveticaFamily

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