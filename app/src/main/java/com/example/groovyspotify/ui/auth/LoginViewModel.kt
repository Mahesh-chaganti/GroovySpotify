package com.example.groovyspotify.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.common.ext.isValidEmail
import com.example.groovyspotify.model.services.AccountService
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.services.common.HandleException
import com.example.groovyspotify.ui.ParentViewModel
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService
) : ParentViewModel(logService) {

    var loginInProgress = mutableStateOf(false)
        private set

    var poppy = mutableStateOf<Event<String>?>(null)
        private set


    var uiState = mutableStateOf(LoginUiState())
        private set



    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password


    init {
        viewModelScope.launch {
//            accountService.currentUserId?.let {
//                firestoreService.getMyUserProfile(it, handleException = {
//                    exception, msg ->
//                    val message = HandleException(exception,msg)
//                    onPopupNotificationChange(message)
//                }){
//                    exception, msg ->
//                    val message = HandleException(exception,msg)
//                    onPopupNotificationChange(message)
//                    //populate cards
//                    // populate chats
//                }
//            }
        }


    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        loginInProgress.value = true
        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(AppText.email_error)
            HandleException(null, "Email is not valid")
            return
        }

        if (password.isBlank()) {
//            SnackbarManager.showMessage(AppText.empty_password_error)
            HandleException(null, "Password is blank")
            return
        }
        viewModelScope.launch {

            accountService.login(email, password, handleException = {
                    exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(popMsg = message)

            }){ // success callback
                    exception, msg ->

                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(popMsg = message)
                //call profiles screen
                openAndPopUp("ProfileScreenLanguagesAndArtists","LoginAuthScreen")


            }

        }

    }
    fun onSignUpScreenButtonClick(openAndPopUp: (String, String) -> Unit){
        openAndPopUp("SignUpScreen","LoginAuthScreen")
    }

    private fun onPopupNotificationChange(popMsg: String) {
        poppy.value = Event(popMsg)
        loginInProgress.value = false
    }

    fun onGoogleSignInClick(credential: AuthCredential) {
        loginInProgress.value = true
        launchCatching {

            accountService.googleSignIn(credential = credential)


        }
        loginInProgress.value = false
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
//            SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }




}