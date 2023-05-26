package com.example.groovyspotify.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class SpotifyAccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Long
)
