package com.example.groovyspotify.ui.spotifyauth

import android.net.Uri
import android.util.Base64
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.groovyspotify.SpotifyWebViewClient

import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

//
//@Composable
//fun SpotifyCallbackScreen(
//    clientId: String,
//    clientSecret: String,
//    redirectUri: String,
//    onAccessTokenReceived: (String) -> Unit,
//    onError: (String) -> Unit,
//    onRefreshTokenReceived: (String) -> Unit
//) {
//    val scope = "user-read-private user-read-email"
//    val stateKey = "spotify_auth_state"
//    val storedState = remember { mutableStateOf("") }
//    val context = LocalContext.current
//
//    val webView = remember {
//        WebView(context).apply {
//            settings.javaScriptEnabled = true
//            webViewClient = SpotifyWebViewClient(redirectUri, onAccessTokenReceived, onError)
//        }
//    }
//
//    val coroutineScope = rememberCoroutineScope()
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "Waiting for callback...")
//        AndroidView(factory = { webView }) { view ->
//            coroutineScope.launch {
//                val url = view.url
//                if (url!!.startsWith(redirectUri)) {
//                    val uri = Uri.parse(url)
//                    val code = uri.getQueryParameter("code")
//                    val state = uri.getQueryParameter("state")
//                    val storedStateValue = storedState.value
//                    if (state == null || state != storedStateValue) {
//                        onError("state_mismatch")
//                    } else {
//                        storedState.value = ""
//                        val authOptionsRequestBody = FormBody.Builder()
//                            .add("code", code ?: "")
//                            .add("redirect_uri", redirectUri)
//                            .add("grant_type", "authorization_code")
//                            .build()
//
//                        val authOptionsRequest = Request.Builder()
//                            .url("https://accounts.spotify.com/api/token")
//                            .header(
//                                "Authorization",
//                                "Basic ${Base64.encodeToString("$clientId:$clientSecret".toByteArray(), Base64.DEFAULT)}"
//                            )
//                            .post(authOptionsRequestBody)
//                            .build()
//
//                        try {
//                            val response = OkHttpClient().newCall(authOptionsRequest).execute()
//                            if (response.isSuccessful) {
//                                val body = response.body?.string()
//                                val accessToken = Json.decodeFromString<AccessTokenResponse>(body ?: "").access_token
//                                val refreshToken =
//                                    Json.decodeFromString<AccessTokenResponse>(body ?: "").refresh_token
//                                onAccessTokenReceived(accessToken)
//
//                                onRefreshTokenReceived(refreshToken)
//                            } else {
//                                onError("invalid_token")
//                            }
//                        } catch (e: IOException) {
//                            onError(e.message ?: "")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
