package com.example.groovyspotify.ui.spotifyauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.SpotifyAccessTokenResponse
import com.example.groovyspotify.model.spotifyapidata.TrackResponse
import com.example.groovyspotify.repository.SpotifyApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyApiViewModel @Inject constructor(private val repository: SpotifyApiRepository): ViewModel() {



    private var _accessTokenResponse : MutableStateFlow<Resource<SpotifyAccessTokenResponse>> = MutableStateFlow(Resource.Loading)
    var accessTokenResponse : StateFlow<Resource<SpotifyAccessTokenResponse>> = _accessTokenResponse

    private var _refreshToken : MutableStateFlow<String> = MutableStateFlow("")
    var refreshToken : StateFlow<String> = _refreshToken

//    fun updateRefreshToken(refreshToken: String) {
//        _accessToken.value = refreshToken
//    }
//
//    fun updateAccessToken(accessTokenResponse: Resource<SpotifyAccessTokenResponse>) {
//        _accessToken.value = accessToken
//    }

    private val _trackState: MutableStateFlow<Resource<TrackResponse>?> = MutableStateFlow(Resource.Loading)
    val trackState: StateFlow<Resource<TrackResponse>?> = _trackState

//    init {
//        if(repository.!= null){
//            _trackState.value = Resource.Success(repository.!!)
//        }
//        if(repository.currentUser != null){
//            _googleState.value = Resource.Success(repository.currentUser!!)
//        }
//
//    }
    fun getTrackData(trackId : String, authorization: String) = viewModelScope.launch {

        _trackState.value = Resource.Loading
        val result = repository.getTrackData(trackId = trackId, authorization = authorization)
        _trackState.value = result
    }
    fun getAccessToken( authorization: String) = viewModelScope.launch {

        _accessTokenResponse.value = Resource.Loading
        val result = repository.getAccessToken(authorization = authorization)
        _accessTokenResponse.value = result
    }


//        try {
//            val response = repository.getTrackData(authorization = "Bearer NgCXRK...MzYjw", trackId = trackId)
//            if (response.) {
//                val track = response.body()
//                _trackState.value = .Success(track)
//            } else {
//                _trackState.value = TrackState.Error("Error occurred")
//            }
//        } catch (e: Exception) {
//            _trackState.value = TrackState.Error("Network request failed")
//        }
//    }
//

}