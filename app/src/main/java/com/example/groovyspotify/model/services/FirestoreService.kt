package com.example.groovyspotify.model.services

import android.net.Uri
import com.example.groovyspotify.model.firestore.UserProfile
import kotlinx.coroutines.flow.Flow

interface FirestoreService {


    suspend fun getMyUserProfile(uid: String, handleException: (Exception?, String) -> Unit, handleSuccess: (Exception?, String) -> Unit): UserProfile?

    suspend fun createOrUpdateMyUserProfile( mapData: Map<String, Any>, handleException: (Exception?, String) -> Unit, handleSuccess: (Exception?, String) -> Unit,)


    suspend fun uploadImageToStorage(imageUri: Uri)

    suspend fun onLike(selectedUser: UserProfile)

    suspend fun onDislike(selectedUser: UserProfile)

    suspend fun populateChats()

    suspend fun populateChat()

    suspend fun depopulateChat()

    suspend fun onSendReply(chatId: String, message: String)

    suspend fun populateCards()






}