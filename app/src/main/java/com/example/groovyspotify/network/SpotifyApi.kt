package com.example.groovyspotify.network

import com.example.groovyspotify.model.spotifyapidata.searchTAP.TrackAlbumPlaylist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Header

import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface SpotifyApi {


//    @GET("tracks/{trackId}")
//    suspend fun getTrackData(
//        @Path("trackId") trackId: String,
//        @Header("Authorization") authorization: String
//    ): TrackResponse

    @GET("search")
    suspend fun getSearchTAPData(
        @Header("Authorization") authorization: String,
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("market") market: String,
        @Query("limit") limit: Int


    ): TrackAlbumPlaylist

}