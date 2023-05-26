package com.example.groovyspotify.data.emailAuth

import com.example.groovyspotify.data.utils.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    val currentUser : FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun signup(name: String, email:String, password: String): Resource<FirebaseUser>

    suspend fun googleSignIn(credential: AuthCredential): Resource<FirebaseUser>

    fun logout()

}