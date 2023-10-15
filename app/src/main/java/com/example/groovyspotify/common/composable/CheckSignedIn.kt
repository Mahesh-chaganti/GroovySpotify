package com.example.groovyspotify.common.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.groovyspotify.ui.auth.LoginViewModel

@Composable
fun CheckSignedIn(loginViewModel: LoginViewModel,openScreen:(String) -> Unit) {
    val alreadyLoggedIn = remember { mutableStateOf(false) }
    val signedIn by loginViewModel.signedIn
    if (signedIn && !alreadyLoggedIn.value) {
        alreadyLoggedIn.value = true
       openScreen("MainHomeScreen")
    }
}