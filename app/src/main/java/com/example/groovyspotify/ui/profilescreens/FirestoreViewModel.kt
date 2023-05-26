package com.example.groovyspotify.ui.profilescreens

import androidx.lifecycle.ViewModel
import com.example.groovyspotify.model.firestore.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(): ViewModel(){


    val firestore = FirebaseFirestore.getInstance()

    suspend fun addUserProfile(userProfile: UserProfile) {
        firestore.collection("userProfiles").document().set(userProfile).await()
    }
}