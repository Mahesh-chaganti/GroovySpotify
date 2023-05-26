package com.example.groovyspotify.data.googleAuth

import android.content.Intent
import android.content.IntentSender
import com.example.groovyspotify.data.utils.FirebaseConstant
import com.example.groovyspotify.data.utils.await
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthUiClient(private val oneTapClient: SignInClient) {
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
           null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): AuthCredential? {

        return try {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val googleIdToken = credential.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
           googleCredentials
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
           null
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(FirebaseConstant.clientId)
                    .build()
            )
            .setAutoSelectEnabled(false)
            .build()
    }
}