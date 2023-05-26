package com.example.groovyspotify.ui.spotifyauth

import androidx.compose.runtime.Composable
import java.util.Random
//
//@Composable
//fun LoginScreen(
//    clientId: String,
//    redirectUri: String,
//    onAccessTokenReceived: (String) -> Unit,
//    onError: (String) -> Unit
//) {
//
//    val stateKey = "spotify_auth_state"
//    val state by remember { mutableStateOf(generateRandomString(16)) }
//    val context = LocalContext.current
//
//    val webView = remember {
//        WebView(context).apply {
//            settings.javaScriptEnabled = true
//            webViewClient = SpotifyWebViewClient(redirectUri, onAccessTokenReceived, onError)
//        }
//    }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "Click the button to log in to Spotify")
//        Button(
//            onClick = {
//                webView.loadUrl(
//                    "https://accounts.spotify.com/authorize?" +
//                            "response_type=code" +
//                            "&client_id=$clientId" +
//                            "&scope=user-read-private%20user-read-email" +
//                            "&redirect_uri=$redirectUri" +
//                            "&state=${state}"
//                )
//            },
//            modifier = Modifier.padding(top = 16.dp)
//        ) {
//            Text(text = "Log in with Spotify")
//        }
//        AndroidView(modifier = Modifier.fillMaxSize(),factory = { webView })
//    }
//}
//private fun generateRandomString(length: Int): String {
//    val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
//    val random = Random()
//    return buildString {
//        repeat(length) {
//            append(possible[random.nextInt(possible.length)])
//        }
//    }
//}