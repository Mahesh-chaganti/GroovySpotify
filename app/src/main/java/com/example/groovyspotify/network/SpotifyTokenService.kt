package com.example.groovyspotify.network

import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.SpotifyAccessTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface SpotifyTokenService {
    @POST("api/token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ):SpotifyAccessTokenResponse
}