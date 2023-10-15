package com.example.groovyspotify.ui.genderanddob

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderAndDobViewModel @Inject constructor(private val firestoreService: FirestoreService): ViewModel() {
    val uiState = mutableStateOf(GenderAndDobState())
    val inProgress = mutableStateOf(false)
    var poppy = mutableStateOf<Event<String>?>(null)
        private set
    fun onAgeChange(age: String){
        uiState.value = uiState.value.copy(age = age)
    }
    fun onGenderChange(gender: String){
        uiState.value = uiState.value.copy(gender = gender)
    }
    fun onPhotoUploadClick(uri: Uri){
        inProgress.value = true
        viewModelScope.launch {
           firestoreService.uploadImageToStorage(imageUri = uri, handleException = {
                   exception, msg ->
               exception?.printStackTrace()
               val errorMsg = exception?.localizedMessage ?: ""
               val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
               onPopupNotificationChange(message)
           }) {
               uiState.value = uiState.value.copy(profilePhotoUrl = it.toString())
                val mapData = mapOf(
                    "profilePhotoUrl" to it.toString()
                )
               viewModelScope.launch {
                   firestoreService.createOrUpdateMyUserProfile(mapData = mapData,handleException = {
                           exception, msg ->
                       exception?.printStackTrace()
                       val errorMsg = exception?.localizedMessage ?: ""
                       val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                       onPopupNotificationChange(message)
                   }){
                           exception, msg ->
                       exception?.printStackTrace()
                       val errorMsg = exception?.localizedMessage ?: ""
                       val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                       onPopupNotificationChange(message)
                   }
               }

            }
        }
    }
    fun onNextClick(openAndPopUp: (String,String) ->Unit){
        inProgress.value = true
        viewModelScope.launch {
            val mapData = mapOf(
                "gender" to uiState.value.gender,
                "age" to uiState.value.age
            )
            firestoreService.createOrUpdateMyUserProfile(mapData, handleException = {exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(message)
            }){exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(message)

                openAndPopUp("MainHomeScreen","GenderAndDobScreen")
            }
        }
    }
    private fun onPopupNotificationChange(popMsg: String) {
        poppy.value = Event(popMsg)
        inProgress.value = false
    }
}