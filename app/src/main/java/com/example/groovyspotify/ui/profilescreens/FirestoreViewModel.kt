package com.example.groovyspotify.ui.profilescreens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.firestore.Contact
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.ui.ParentViewModel
import com.example.groovyspotify.ui.home.CircularDotsAnimation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(logService: LogService,private val firestoreService: FirestoreService) : ParentViewModel(logService) {

    var contacts by mutableStateOf(emptyList<Contact>())
//
//    private val _matchedContacts = MutableStateFlow(mutableStateOf(MatchedContact()))
//    val matchedContacts: StateFlow<MutableList<MatchedContact>> = _matchedContacts


    val storage = FirebaseStorage.getInstance()
    val storageRef: StorageReference = storage.reference


    private val _listOfRecommendedProfiles = MutableStateFlow<Resource<ArrayList<UserProfile>>?>(null)
    val listOfRecommendedProfiles: StateFlow<Resource<ArrayList<UserProfile>>?> = _listOfRecommendedProfiles

    private val _myUserProfile = MutableStateFlow<Resource<UserProfile>?>(null)
    val myUserProfile: StateFlow<Resource<UserProfile>?> = _myUserProfile


   fun getMyUserProfile(email: String) {
       launchCatching {

       }
    }



    private val _downloadUrlFlow = MutableStateFlow<String>("")
    val downloadUrlFlow: StateFlow<String> = _downloadUrlFlow


    fun createOrUpdateMyUserProfile( mapData: Map<String, Any>) {
        launchCatching {
//                inProgress = value
//                firestoreService.createOrUpdateMyUserProfile(mapData)

            populateCards()
        }
    }

    fun populateCards(){

    }

    suspend fun uploadImageToStorage(
        imageUri: Uri,
        fileName: String,

        ) = viewModelScope.launch {
        val fileRef = storageRef.child("FirestoreProfilePhotos/$fileName")

        fileRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                // Get the download URL of the uploaded image
                fileRef.downloadUrl
                    .addOnSuccessListener { uri ->

                        _downloadUrlFlow.value = uri.toString()


                    }
                    .addOnFailureListener { exception ->
                        Log.d("Firestoreviewmodel", "uploadImageToStorage: ")
                    }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestoreviewmodel", "uploadImageToStorage: ")
            }.await()
    }


    suspend fun getAllOtherUserProfiles() = viewModelScope.launch {

        val result = arrayListOf<UserProfile>()
        _myUserProfile.value.let {
            when (it) {
                is Resource.Failure -> {

                    Log.d("FirestoreVM", "getAllOtherUserProfiles: ${it.exception.message}")
                }

                Resource.Loading -> {
                    Log.d("FirestoreVM", "getAllOtherUserProfiles: Loading")
                }

                is Resource.Success -> {
//                    firestore.collection("UserProfiles")
//                        .whereNotEqualTo("userName", it.data.userName).get()
//                        .addOnSuccessListener { it ->
//                            _listOfRecommendedProfiles.value = Resource.Loading
//                            it.documents.forEachIndexed { index, document ->
//                                val userProfile = document.toObject(UserProfile::class.java)
//
//                                result.add(userProfile!!)
//                            }
//                            _listOfRecommendedProfiles.value = Resource.Success(result)
//                        }
//                        .addOnFailureListener {
//                            _listOfRecommendedProfiles.value = Resource.Failure(exception = it)
//                        }.await()
//

                }

                else -> {}
            }
        }


    }


}

