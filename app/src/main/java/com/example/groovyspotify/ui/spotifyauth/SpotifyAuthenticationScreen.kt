package com.example.groovyspotify.ui.spotifyauth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.groovyspotify.data.utils.SpotifyConstant

//
//@Composable
//fun SpotifyAuthenticationScreen(
//    spotifyApiViewModel: SpotifyApiViewModel,
//    navController: NavController
//) {
//    val clientId = SpotifyConstant.clientId// Replace with your client ID
//    val clientSecret = SpotifyConstant.clientSecret // Replace with your client secret
//    val redirectUri = SpotifyConstant.redirectURI // Replace with your redirect URI
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
////        var accessTokenState = spotifyApiViewModel.accessToken.collectAsState()
//
//        var authCode by remember { mutableStateOf("") }
//        if (accessTokenState.value.isEmpty()) {
//            LoginScreen(
//                clientId = clientId,
//                redirectUri = redirectUri,
//                onAccessTokenReceived = { code ->
//                    // Handle the received authorization code and obtain access token
//              authCode = code
//
//                },
//                onError = { error ->
//                    // Handle errors
//                    Log.d("SpotLoginScreen", "SpotifyAuthenticationScreen: $error")
//                }
//            )
//        } else {
//            // User is already logged in, display the main screen
//            // TODO: Implement the main screen using Jetpack Compose
//            HomeScreen( viewModel = null,spotifyApiViewModel = spotifyApiViewModel,navController = navController)
//        }
//        if(authCode != ""){
//            SpotifyCallbackScreen(
//                clientId = SpotifyConstant.clientId,
//                clientSecret = SpotifyConstant.clientSecret,
//                redirectUri = SpotifyConstant.redirectURI,
//                onAccessTokenReceived = { accessToken ->
//                    // Handle the received access token
////                  spotifyApiViewModel.updateAccessToken(accessToken = accessToken)
//
//                },
//                onError = { error ->
//                    // Handle errors
//                    Log.d("SpotCallBackScreen", "SpotifyAuthenticationScreen: $error")
//                },
//                onRefreshTokenReceived = { refreshToken ->
//                    // Handle the received refresh token
//                    // This callback is optional based on your use case
////                    spotifyApiViewModel.updateRefreshToken(refreshToken = refreshToken)
//                }
//            )
//        }


//    }
//}







//
//@Composable
//fun InitiateAuthenticationFlow(clientId: String, redirectUri: String, context: Context) {
//    val state = generateRandomString(16)
//    val scope = "user-read-private user-read-email"
//    val authorizationEndpoint =
//        "https://accounts.spotify.com/authorize?" +
//                "response_type=code" +
//                "&client_id=$clientId" +
//                "&scope=$scope" +
//                "&redirect_uri=$redirectUri" +
//                "&state=$state"
//
//    // Launch web view or custom browser with the authorizationEndpoint URL
//    WebViewComponent(url = authorizationEndpoint)
//}
//
//@Composable
//fun WebViewComponent(url: String) {
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { context ->
//            WebView(context).apply {
//                settings.javaScriptEnabled = true
//                webViewClient = SpotifyWebViewClient(redirectUri, onAccessTokenReceived, onError)
//            }
//        }
//    )
//}

//
//private suspend fun getToken(
//    spotifyAuthViewModel: SpotifyAuthViewModel,
//    code: String,
//    redirectUri: String
//): String {
//    val authOptions = mapOf(
//        "url" to "https://accounts.spotify.com/api/token",
//        "form" to mapOf(
//            "code" to code,
//            "redirect_uri" to redirectUri,
//            "grant_type" to "authorization_code"
//        ),
//        "headers" to mapOf(
//            "Authorization" to "Basic " + Base64.encodeToString(
//                "$clientId:$clientSecret".toByteArray(),
//                Base64.DEFAULT
//            )
//        ),
//        "json" to true
//    )
//
//    return withContext(Dispatchers.IO) {
//        // Make POST request to authOptions['url'] with authOptions['form'] and authOptions['headers']
//        // Retrieve the access token from the response and return it
//
//        spotifyAuthViewModel.postrequest(
//            url = authOptions["url"].toString(),
//            fields = authOptions["form"] as Map<String, String>,
//            headers = authOptions["headers"] as Map<String, String>
//        ).toString()
//
//
//    }
//}
//
//private suspend fun fetchUserProfile(spotifyAuthViewModel: SpotifyAuthViewModel,accessToken: String): String? {
//    val options = mapOf(
//        "url" to "https://api.spotify.com/v1/me",
//        "headers" to mapOf("Authorization" to "Bearer $accessToken"),
//        "json" to true
//    )
//
//    return withContext(Dispatchers.IO) {
//        // Make GET request to options['url'] with options['headers']
//        // Parse the response and return the user profile
//        spotifyAuthViewModel.getData(url = options["url"].toString() , headers = options["headers"] as Map<String, String>).toString()
//    }
//}
//
//@Composable
//fun AuthenticationSuccessScreen(accessToken: String) {
//    var userProfile by remember { mutableStateOf<String?>(null) }
//
//    LaunchedEffect(Unit) {
////        val profile = fetchUserProfile(accessToken)
//        userProfile = null
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Authentication Successful!",
//            style = MaterialTheme.typography.h5,
//            modifier = Modifier.padding(16.dp),
//            textAlign = TextAlign.Center
//        )
//
//        userProfile?.let { profile ->
//            Text(
//                text = "Welcome, ${profile}!",
//                style = MaterialTheme.typography.body1,
//                modifier = Modifier.padding(16.dp),
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}


//    return (0 until length)
//        .map { possible[random.nextInt(possible.length)] }
//        .joinToString("")

