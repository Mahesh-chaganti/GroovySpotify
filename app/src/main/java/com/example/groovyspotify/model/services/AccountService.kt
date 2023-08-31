package com.example.groovyspotify.model.services

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser


interface AccountService {
    val currentUserId: String?
    val currentUser : FirebaseUser?

    suspend fun login(email: String, password: String, handleException: (Exception?,String) -> Unit,handleSuccess: (Exception?,String) -> Unit)

    suspend fun signup( username: String, email:String, password: String, handleException: (Exception?,String) -> Unit,handleSuccess: (Exception?,String) -> Unit)


    suspend fun googleSignIn(credential: AuthCredential)

    suspend fun sendRecoveryEmail(email: String)

    suspend fun deleteAccount()

    fun logout()

}