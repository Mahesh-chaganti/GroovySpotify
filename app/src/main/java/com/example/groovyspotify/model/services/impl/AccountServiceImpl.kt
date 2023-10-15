package com.example.groovyspotify.model.services.impl

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.firestore.COLLECTION_NAMES.COLLECTION_PROFILES
import com.example.groovyspotify.model.services.AccountService
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.common.HandleException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth,private val firestore: FirebaseFirestore) : AccountService {


    override val currentUserId: String?
        get() = auth.currentUser?.uid
    override val currentUser: FirebaseUser?
        get() = auth.currentUser



    override suspend fun login(email: String, password: String,handleException: (Exception?, String) -> Unit,handleSuccess: (Exception?, String) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
            if(task.isSuccessful){
                Log.d("SuccessTask", "Login: ${task.exception}")
                handleSuccess(null,"Login successful")

            }
            else{
                Log.d("FailureTask", "Login: ${task.exception}")
                handleException(task.exception, "Login failed")
            }

        }.addOnFailureListener {
            handleException(it, "Login failed")
        }
    }

    override suspend fun signup(
        username: String,
        email: String,
        password: String,
        handleException: (Exception?, String) -> Unit,
        handleSuccess: (Exception?,String) -> Unit
    ) {
        firestore.collection(COLLECTION_PROFILES).whereEqualTo("userName", username)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("SuccessTask", "signup: ${task.exception}")
                                handleSuccess(null, "Signup successful")
                            } else {
                                Log.d("FailureTask", "signup: ${task.exception}")
                                handleException(task.exception, "Signup failed")
                            }

                        }
                }
                else
                    handleException(null,  "username already exists")
            }
            .addOnFailureListener {it ->
                handleException(it,"")
            }
    }

    override suspend fun googleSignIn(credential: AuthCredential) {
       auth.signInWithCredential(credential).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }


    override suspend fun logout() {

            auth.signOut()


    }



}
