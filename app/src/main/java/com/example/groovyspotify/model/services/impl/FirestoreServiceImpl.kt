package com.example.groovyspotify.model.services.impl

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.firestore.COLLECTION_NAMES
import com.example.groovyspotify.model.firestore.COLLECTION_NAMES.COLLECTION_PROFILES
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.services.AccountService
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.services.common.HandleException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor(private val authService: AccountService,private val storage: FirebaseStorage, private val firestore: FirebaseFirestore): FirestoreService {
    override suspend fun getMyUserProfile(uid: String, handleException: (Exception?, String) -> Unit, handleSuccess: (Exception?, String) -> Unit): UserProfile?{
        var user: UserProfile? = null
        firestore.collection(COLLECTION_PROFILES).document(uid)
            .addSnapshotListener { value, error ->
                if (error != null)
                    handleException(error, "Cannot retrieve user data")

                if (value != null) {
                    user = value.toObject<UserProfile>()
                    handleSuccess(null, "Got the user")

                }

            }
        return user
    }

    override suspend fun createOrUpdateMyUserProfile( mapData: Map<String, Any>, handleException: (Exception?, String) -> Unit, handleSuccess: (Exception?, String) -> Unit) {
//        inProgress.value = true
           authService.currentUserId?.let {uid->
               firestore.collection(COLLECTION_PROFILES).document(uid)
                   .get()
                   .addOnSuccessListener {
                       if (it.exists())
                           it.reference.update(mapData)
                               .addOnSuccessListener {
                                    handleSuccess(null,"Updated user")
//                                   inProgress.value = false
//                                   populateCards()
                               }
                               .addOnFailureListener {
                                   handleException(it, "Cannot update user")
                               }
                       else {
                           firestore.collection(COLLECTION_PROFILES).document(uid).set(mapData)
                           handleSuccess(null, "created user")
//                           inProgress.value = false
//                           getMyUserProfile()
                       }
                   }
                   .addOnFailureListener {
                       handleException(it, "Cannot create user")
                   }
           }

    }

    override suspend fun uploadImageToStorage(imageUri: Uri) {

    }

    override suspend fun onLike(selectedUser: UserProfile) {
    }

    override suspend fun onDislike(selectedUser: UserProfile) {
    }

    override suspend fun populateChats() {
    }

    override suspend fun populateChat() {
    }

    override suspend fun depopulateChat() {
    }

    override suspend fun onSendReply(chatId: String, message: String) {
    }

    override suspend fun populateCards() {
        
    }




}