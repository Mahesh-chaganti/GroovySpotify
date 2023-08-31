package com.example.groovyspotify.ui.profilescreens.audio

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.data.utils.SpotifyConstant
import com.example.groovyspotify.model.SpotifyAccessTokenResponse
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.spotifyapidata.searchTAP.TrackAlbumPlaylist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.example.groovyspotify.repository.SpotifyApiRepository
import com.example.groovyspotify.ui.ParentViewModel
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FeaturedAudioViewModel @Inject constructor(
    logService: LogService,
    private val repository: SpotifyApiRepository,
    private val firestoreService: FirestoreService
) : ParentViewModel(logService) {
    var uiState = mutableStateOf(FeaturedAudioUiState())
        private set

    var openDialog = mutableStateOf(false)
        private set

    var poppy = mutableStateOf<Event<String>?>(null)
        private set

    val clientId = SpotifyConstant.clientId // Your client id
    val clientSecret = SpotifyConstant.clientSecret// Your secret
    val authHeader =
        "Basic " + Base64.encodeToString("$clientId:$clientSecret".toByteArray(), Base64.NO_WRAP)
    val accessToken = mutableStateOf(SpotifyAccessTokenResponse())
    val inProgress = mutableStateOf(false)
    init{
        inProgress.value = true
        viewModelScope.launch {

            val result = repository.getAccessToken(authHeader, handleException = {
                    exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(popMsg = message)

            }){
                    exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(popMsg = message)

            }
            accessToken.value = result
            Log.d("Token", "AccessTokenValue: ${accessToken.value}")
        }
       
    }
    fun onSearchValueChange(searchString: String) {
            uiState.value = uiState.value.copy(searchString)
            getTAPData(uiState.value.searchQuery)

    }

    fun getTAPData(query: String) {
        inProgress.value = true
        viewModelScope.launch{

            uiState.value.tapData = repository.getSearchTAPData(
                authorization = "Bearer ${accessToken.value.accessToken}",
                query = query,
                type = "album,track,playlist",
                limit = 10,
                market = "IN",
                handleException = {
                        exception, msg ->
                    exception?.printStackTrace()
                    val errorMsg = exception?.localizedMessage ?: ""
                    val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                    onPopupNotificationChange(popMsg = message)

                }
            ){
                    exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(popMsg = message)

            }

        }

    }

    fun onTrackClick(trackResponse: TrackResponse){
           uiState.value.clickedTrack = trackResponse
        openDialog.value = true
    }

    fun onSetTrack(trackResponse: TrackResponse)
    {
        uiState.value.featuredTrack = trackResponse
    }
    fun onDismissClick(){
        openDialog.value = false
    }
    fun onConfirmClick(){
        openDialog.value = false
        inProgress.value = true
        val mapData = mapOf(
            "track" to mapOf(
                "album" to uiState.value.featuredTrack.album.name,
                "artists" to uiState.value.featuredTrack.artists,
                "external_urls" to uiState.value.featuredTrack.external_urls,
                "id" to uiState.value.featuredTrack.id,
                "name" to uiState.value.featuredTrack.name,
                "preview_url" to uiState.value.featuredTrack.preview_url,
                "images" to uiState.value.featuredTrack.album.images,
                "release_date" to uiState.value.featuredTrack.album.release_date

            )
        )
        viewModelScope.launch {
            firestoreService.createOrUpdateMyUserProfile(mapData, handleException = {
                    exception, msg ->

                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(popMsg = message)
            }){ exception, msg ->

                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(popMsg = message)
            }
        }

    }

    private fun onPopupNotificationChange(popMsg: String) {
            poppy.value = Event(popMsg)
            inProgress.value = false
    }


}