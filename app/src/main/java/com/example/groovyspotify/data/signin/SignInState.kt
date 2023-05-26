package com.example.groovyspotify.data.signin


data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
