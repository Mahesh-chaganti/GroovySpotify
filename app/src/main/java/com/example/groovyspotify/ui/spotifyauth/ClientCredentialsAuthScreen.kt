package com.example.groovyspotify.ui.spotifyauth

import android.util.Base64
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.groovyspotify.data.utils.SpotifyConstant
import kotlinx.coroutines.launch

//@Composable
//fun ClientCredentialsAuthScreen(spotifyApiViewModel: SpotifyApiViewModel, navController: NavController) {
//
//
//
//
// val scope = rememberCoroutineScope()
//    Button(onClick = {
//        scope.launch {
//            try {
//                val authRequest = spotifyApiViewModel.getAccessToken(authHeader)
//
//                    // Handle authentication request failure here
//
//            } catch (e: Exception) {
//                // Handle network or API-related errors here
//                Log.d("ClientAccessTokenCall", "ClientCredentialsAuthScreen: $e")
//            }
//        }
//        navController.navigate("HomeScreen")
//    }
//    ){
//        Text(
//            text = "Lets Launch",
//            fontSize = 28.sp,
//            modifier = Modifier.fillMaxWidth().height(200.dp),
//            color = Color.White
//        )
//    }
//
//
//}