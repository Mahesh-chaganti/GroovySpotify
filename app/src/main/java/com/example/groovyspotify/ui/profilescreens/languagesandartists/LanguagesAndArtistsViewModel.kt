package com.example.groovyspotify.ui.profilescreens.languagesandartists

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.model.profile.ProfileArtist
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.services.common.HandleException
import com.example.groovyspotify.ui.ParentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguagesAndArtistsViewModel @Inject constructor(logService: LogService,private val firestoreService: FirestoreService): ParentViewModel(logService){
    var uiState = mutableStateOf(LangAndArtistsUiState())
        private set

    var updateInProgress = mutableStateOf(false)
        private set

    var poppy = mutableStateOf<Event<String>?>(null)
        private set

    fun addArtist(artist: ProfileArtist){
        if (uiState.value.listOfArtists.contains(artist)) {
            uiState.value.listOfArtists.remove(artist)
        }
        else{
            uiState.value.listOfArtists.add(artist)
        }
    }
    fun addLanguage(language: String){
        if (uiState.value.listOfLanguages.contains(language)) {
            uiState.value.listOfLanguages.remove(language)
        }
        else{
            uiState.value.listOfLanguages.add(language)
        }

    }
    fun onNextClick(openAndPopUp:(String,String)-> Unit){
        //send data to firestore
        val mapData = mapOf(
            "favArtists" to uiState.value.listOfArtists,
            "favLanguages" to uiState.value.listOfLanguages
        )
        updateInProgress.value = true
        viewModelScope.launch{
            firestoreService.createOrUpdateMyUserProfile(mapData = mapData, handleException = {
                exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(message)

            }){exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(message)
                openAndPopUp("ProfileFeaturedAudio","ProfileScreenLanguagesAndArtists")
            }

        }



    }
    private fun onPopupNotificationChange(popMsg: String) {
        poppy.value = Event(popMsg)
        updateInProgress.value = false
    }

}