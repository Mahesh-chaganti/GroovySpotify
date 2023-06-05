package com.example.groovyspotify.ui.profilescreens

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class FirestoreViewModel @Inject constructor() : ViewModel() {

    var myUsername by mutableStateOf<String?>("")
    var favoriteArtists by  mutableStateOf(ArrayList<String>())

    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val storageRef: StorageReference = storage.reference


    private val _mapOfEachProfileArtists = MutableStateFlow(ArrayList<Map<String, List<String>>>())
    val mapOfEachProfileArtists : StateFlow<ArrayList<Map<String,List<String>>>> = _mapOfEachProfileArtists



    private val _downloadUrlFlow = MutableStateFlow<String>("")
    val downloadUrlFlow: StateFlow<String> = _downloadUrlFlow

    fun updateMyUsername(userName: String) {
        myUsername = userName
    }

    fun updateMyFavArtists(favArtists: ArrayList<String>) {
        favoriteArtists = favArtists
    }

    suspend fun updateUserProfile(userName: String, mapData: Map<String, Any>) {
        firestore.collection("UserProfiles")
            .document(userName)
            .update(mapData)
            .addOnSuccessListener {
                Log.d("FirestoreviewmodelSuc", "updateUserProfile: $it")
            }
            .addOnFailureListener {
                Log.d("FirestoreviewmodelFail", "updateUserProfile: $it")
            }
            .await()


    }

    suspend fun createUserProfile(userName: String, mapData: Map<String, Any>) {
        firestore.collection("UserProfiles")
            .document(userName)
            .set(mapData)
            .addOnSuccessListener {
                Log.d("FirestoreviewmodelSuc", "updateUserProfile: $it")
            }
            .addOnFailureListener {
                Log.d("FirestoreviewmodelFail", "updateUserProfile: $it")
            }
            .await()


    }



    suspend fun uploadImageToStorage(
        imageUri: Uri,
        fileName: String,

    ) {
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


    suspend fun getUserProfile() = viewModelScope.launch{
        firestore.collection("UserProfiles").whereNotEqualTo("userName" , myUsername).get().addOnSuccessListener { it ->
            it.documents.forEachIndexed { index, document ->
                val userProfile = document.toObject(UserProfile::class.java)

                val myArtists = userProfile!!.favoriteArtists
                val myProfileUserName = userProfile!!.userName


                val map = mapOf(myProfileUserName to myArtists)
                _mapOfEachProfileArtists.value.add(map as Map<String, List<String>>)

            }
        }.await()
    }

}