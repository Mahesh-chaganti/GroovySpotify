package com.example.groovyspotify.network

import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.spotifyapidata.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Header

import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface SpotifyApi {


    @GET("tracks/{trackId}")
    suspend fun getTrackData(
        @Path("trackId") trackId: String,
        @Header("Authorization") authorization: String
    ): TrackResponse
}