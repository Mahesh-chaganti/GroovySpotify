package com.example.groovyspotify.repository

import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.SpotifyAccessTokenResponse
import com.example.groovyspotify.model.spotifyapidata.searchTAP.TrackAlbumPlaylist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.example.groovyspotify.network.SpotifyApi
import com.example.groovyspotify.network.SpotifyTokenService
import javax.inject.Inject

class SpotifyApiRepository @Inject constructor(private val api: SpotifyApi,private val spotifyTokenService: SpotifyTokenService) {
    suspend fun getAccessToken( authorization: String): Resource<SpotifyAccessTokenResponse>{
        val result : SpotifyAccessTokenResponse =
            try {
             spotifyTokenService.getAccessToken(authorization = authorization)


        }catch(e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
        return Resource.Success(data = result)
    }

    suspend fun getSearchTAPData(query : String, authorization: String, type: String, limit: Int, market: String): Resource<TrackAlbumPlaylist> {

            val result : TrackAlbumPlaylist =
                try {
                    api.getSearchTAPData(authorization = authorization, query = query, type = type, limit = limit, market = market  )


        }catch(e: Exception){
            e.printStackTrace()
           return  Resource.Failure(e)
        }
        return Resource.Success(data = result)
    }

}