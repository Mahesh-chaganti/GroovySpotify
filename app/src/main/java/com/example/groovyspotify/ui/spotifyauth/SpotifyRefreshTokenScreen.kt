package com.example.groovyspotify.ui.spotifyauth

import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

@Composable
fun RefreshTokenScreen(
    spotifyApiViewModel: SpotifyApiViewModel,
    clientId: String,
    clientSecret: String,
    onAccessTokenReceived: (String) -> Unit,
    onError: (String) -> Unit
) {
//    val refresh_token = spotifyApiViewModel.refreshToken.collectAsState()// Provide the refresh token value here
//    val authOptionsRequestBody = FormBody.Builder()
//        .add("grant_type", "refresh_token")
//        .add("refresh_token", refresh_token.value)
//        .build()
//
//    val authOptionsRequest = Request.Builder()
//        .url("https://accounts.spotify.com/api/token")
//        .header(
//            "Authorization",
//            "Basic ${Base64.encodeToString("$clientId:$clientSecret".toByteArray(),
//                Base64.DEFAULT)}"
//        )
//        .post(authOptionsRequestBody)
//        .build()
//
//    try {
//        val response = OkHttpClient().newCall(authOptionsRequest).execute()
//        if (response.isSuccessful) {
//            val body = response.body?.string()
//            val accessToken = Json.decodeFromString<AccessTokenResponse>(body ?: "").access_token
//            onAccessTokenReceived(accessToken)
//            spotifyApiViewModel.updateAccessToken(accessToken)
//        } else {
//            onError("Failed to refresh token")
//        }
//    } catch (e: IOException) {
//        onError(e.message ?: "")
//    }
}
