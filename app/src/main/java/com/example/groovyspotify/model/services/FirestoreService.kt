package com.example.groovyspotify.model.services

import android.net.Uri
import com.example.groovyspotify.model.firestore.ChatData
import com.example.groovyspotify.model.firestore.UserProfile
import kotlinx.coroutines.flow.Flow

interface FirestoreService {
    val userData: UserProfile
        get() = UserProfile()

    suspend fun getMyUserProfile(uid: String, handleException: (Exception?, String) -> Unit, handleSuccess: (UserProfile, String) -> Unit): UserProfile

    suspend fun createOrUpdateMyUserProfile( mapData: Map<String, Any>, handleException: (Exception?, String) -> Unit, handleSuccess: (Exception?, String) -> Unit,)


    suspend fun uploadImageToStorage(imageUri: Uri,handleException: (Exception?, String) -> Unit,onSuccess: (Uri) -> Unit)

    suspend fun onLike(selectedUser: UserProfile, handleException: (String) -> Unit,handleSuccess: (UserProfile) -> Unit)

    suspend fun onDislike(selectedUser: UserProfile)

    suspend fun populateChats(handleException: (Exception?, String) -> Unit,
                              handleSuccess: (List<ChatData>) -> Unit)

    suspend fun populateChat(chatId: String,handleException: (Exception?, String) -> Unit,
                             handleSuccess: (Exception?, String) -> Unit)

    suspend fun depopulateChat()
    suspend fun addToChats(user1: UserProfile,user2:UserProfile)

    suspend fun onSendReply(chatId: String, message: String)

    suspend fun populateCards(handleException: (Exception?, String) -> Unit, handleSuccess: (List<UserProfile>, String) -> Unit):List<UserProfile>






}