package com.example.groovyspotify.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.R
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.profilescreens.ContactSyncScreen
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.profilescreens.PhotoUploadScreen
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import com.google.firebase.firestore.FirebaseFirestore
import font.helveticaFamily
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(viewModel: AuthViewModel?, firestoreViewModel: FirestoreViewModel?, navController: NavController) {
    val scope = rememberCoroutineScope()
////    val trackState = spotifyApiViewModel?.trackState!!.collectAsState()
//    var accessToken by remember{ mutableStateOf("") }
//    val accessTokenState = spotifyApiViewModel?.accessTokenResponse?.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//
//                        Color(0xFF000000),
//                        Color(0xFFFFFFFF)
//                    )
//                )
            )
    )
//    accessTokenState?.value.let {
//        when (it) {
//            is Resource.Failure -> {
//                val context = LocalContext.current
//                Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
//            }
//
//            Resource.Loading -> {
//                CircularProgressIndicator()
//            }
//
//            is Resource.Success -> {
//
//                accessToken = it.data.accessToken
//            }
//
//            else -> {}
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .padding(18.dp)
                .size(24.dp)
                .clickable { navController.popBackStack() },
            painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
            contentDescription = "Back button",
            tint = Color(0xFFFF5722)
        )
        Text(
            text = "Welcome back",
            style = MaterialTheme.typography.body1,
            color = Color.White
        )

        Text(
            text = viewModel?.currentUser?.displayName ?: "",
            style = MaterialTheme.typography.body1,
            color = Color.White
        )

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Name",
                    fontSize = 28.sp,
                    modifier = Modifier.weight(0.3f),
                    color = Color.White
                )

                Text(
                    text = viewModel?.currentUser?.displayName ?: "",
                    fontSize = 28.sp,
                    modifier = Modifier.weight(0.7f),
                    color = Color.White
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Email",
                    fontSize = 28.sp,
                    modifier = Modifier.weight(0.3f),
                    color = Color.White
                )

                Text(
                    text = viewModel?.currentUser?.email ?: "",
                    fontSize = 28.sp,
                    modifier = Modifier.weight(0.7f),
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    viewModel?.logout()
                    navController.navigate("LoginAuthScreen") {
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
//            PhotoUploadScreen()
//            ContactSyncScreen()
            Button(
                onClick = {
//                    scope.launch {
//                        firestoreViewModel?.addUserProfile(
//                            userProfile = UserProfile(
//                                name = viewModel?.currentUser?.displayName,
//                                email = viewModel?.currentUser?.email,
//                                phone = null,
//                                profilePhotoUrl = null,
//                                favoriteArtists = null,
//                                myLanguages = null,
//                                username = null,
//                                featuredAudio = null
//                            )
//                        )
//                    }

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Add me",
                    fontSize = 18.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium
                )
            }

//            Column {
//                Button(onClick = {
////                    spotifyApiViewModel.getTrackData(trackId = "11dFghVXANMlKmJXsNCbNl", authorization = "Bearer $accessToken")
//                }) {
//                    Text(
//                        text = "Click me to get Track data",
//                        fontSize = 28.sp,
//                        modifier = Modifier.fillMaxWidth().height(200.dp),
//                        color = Color.White
//                    )
//                }
//                trackState?.value.let {
//                    when (it) {
//                        is Resource.Failure -> {
//                            val context = LocalContext.current
//                            Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
//                        }
//
//                        Resource.Loading -> {
//                            CircularProgressIndicator()
//                        }
//
//                        is Resource.Success -> {
//                            Column() {
//
//
//                                Text(
//                                    text = it.data.album.name,
//                                    fontSize = 28.sp,
//                                    modifier = Modifier.weight(0.7f),
//                                    color = Color.White
//                                )
//                                Text(
//                                    text = it.data.id,
//                                    fontSize = 28.sp,
//                                    modifier = Modifier.weight(0.7f),
//                                    color = Color.White
//                                )
//                            }
//                        }
//
//                        else -> {}
//                    }
//                }
            }
        }
    }



@Preview
@Composable
fun HomeScreenPreview() {

    HomeScreen(null, firestoreViewModel = null,rememberNavController())


}