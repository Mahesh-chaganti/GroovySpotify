package com.example.groovyspotify.repository

import android.util.Log
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.SpotifyAccessTokenResponse
import com.example.groovyspotify.model.spotifyapidata.searchTAP.TrackAlbumPlaylist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.example.groovyspotify.network.SpotifyApi
import com.example.groovyspotify.network.SpotifyTokenService
import javax.inject.Inject

class SpotifyApiRepository @Inject constructor(
    private val api: SpotifyApi,
    private val spotifyTokenService: SpotifyTokenService
) {
    suspend fun getAccessToken(
        authorization: String,
        handleException: (Exception?, String) -> Unit,
        handleSuccess: (Exception?, String) -> Unit
    ): SpotifyAccessTokenResponse {
        val result: SpotifyAccessTokenResponse

        try {
            result = spotifyTokenService.getAccessToken(authorization = authorization)
            handleSuccess(null, "Access token retreived")
            Log.d("Token success", "getAccessToken: Token success")

        } catch (e: Exception) {
            handleException(e, "Failed to get access token")
            Log.d("Token failed", "getAccessToken: $e")
            e.printStackTrace()
            return SpotifyAccessTokenResponse()
        }
        return result
    }

    //    suspend fun getSearchTAPData(query : String): TrackAlbumPlaylist {
//        var result: TrackAlbumPlaylist = TrackAlbumPlaylist()
////        _tapState.value = Resource.Loading
//
//             result = api.getSearchTAPData(
//                authorization = "Bearer $accessToken",
//                query = query,
//                type = "album,track,playlist",
//                limit = 10,
//                market = "IN"
//            )
//
//
//        return result
////        _tapState.value = result
//    }
    suspend fun getSearchTAPData(
        query: String, authorization: String, type: String, limit: Int, market: String,
        handleException: (Exception?, String) -> Unit, handleSuccess: (Exception?, String) -> Unit
    ): TrackAlbumPlaylist {

        val result: TrackAlbumPlaylist
        try {
            result = api.getSearchTAPData(
                authorization = authorization,
                query = query,
                type = type,
                limit = limit,
                market = market
            )
            Log.d("Search", "Search success")
            handleSuccess(null, "Search Success")

        } catch (e: Exception) {
            handleException(e, "Search failed")
            Log.d("Search", "Search Failed: $e")

            e.printStackTrace()
            return TrackAlbumPlaylist()
        }
        return result
    }

}