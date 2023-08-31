package com.example.groovyspotify.ui.spotifyauth

import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.data.utils.SpotifyConstant
import com.example.groovyspotify.model.SpotifyAccessTokenResponse
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.model.spotifyapidata.searchTAP.TrackAlbumPlaylist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.example.groovyspotify.repository.SpotifyApiRepository
import com.example.groovyspotify.ui.ParentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyApiViewModel @Inject constructor(logService: LogService,private val repository: SpotifyApiRepository): ParentViewModel(logService) {

    val clientId = SpotifyConstant.clientId // Your client id
    val clientSecret = SpotifyConstant.clientSecret// Your secret
    val authHeader =
        "Basic " + Base64.encodeToString("$clientId:$clientSecret".toByteArray(), Base64.NO_WRAP)
    var accessToken = mutableStateOf(SpotifyAccessTokenResponse())
//    private var _accessTokenResponse : MutableStateFlow<Resource<SpotifyAccessTokenResponse>> = MutableStateFlow(Resource.Loading)
//    var accessTokenResponse : StateFlow<Resource<SpotifyAccessTokenResponse>> = _accessTokenResponse

//    private var _refreshToken : MutableStateFlow<String> = MutableStateFlow("")
//    var refreshToken : StateFlow<String> = _refreshToken

//    fun updateRefreshToken(refreshToken: String) {
//        _accessToken.value = refreshToken
//    }
//
//    fun updateAccessToken(accessTokenResponse: Resource<SpotifyAccessTokenResponse>) {
//        _accessToken.value = accessToken
//    }

//    private val _trackState: MutableStateFlow<Resource<TrackResponse>?> = MutableStateFlow(Resource.Loading)
//    val trackState: StateFlow<Resource<TrackResponse>?> = _trackState

    private val _tapState: MutableStateFlow<Resource<TrackAlbumPlaylist>?> = MutableStateFlow(Resource.Loading)
    val tapState: StateFlow<Resource<TrackAlbumPlaylist>?> = _tapState

    init{
//        launchCatching {
//        val result = repository.getAccessToken(authHeader)
//            accessToken.value = result
//
//        }

    }



    fun getSearchTAPData(query : String): TrackAlbumPlaylist {
        var result = TrackAlbumPlaylist()
       viewModelScope.launch{

//        result = repository.getSearchTAPData(
//                authorization = "Bearer $accessToken",
//                query = query,
//                type = "album,track,playlist",
//                limit = 10,
//                market = "IN"
//            )

        }
     return result
//        _tapState.value = result
    }

}