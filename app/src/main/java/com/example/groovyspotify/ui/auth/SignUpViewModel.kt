package com.example.groovyspotify.ui.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.common.ext.isValidEmail
import com.example.groovyspotify.common.ext.isValidPassword
import com.example.groovyspotify.common.ext.passwordMatches
import com.example.groovyspotify.model.services.AccountService
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.services.common.HandleException
import com.example.groovyspotify.ui.ParentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.groovyspotify.R.string as AppText


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService
) : ParentViewModel(logService) {
    var signUpInProgress = mutableStateOf(false)
        private set

    var poppy = mutableStateOf<Event<String>?>(null)
        private set




    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val username
        get() = uiState.value.userName
    private val confirmPassword
        get() = uiState.value.repeatPassword



    fun onUserNameChange(newValue: String) {
        uiState.value = uiState.value.copy(userName = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun ColorOfSignupButton(): Boolean{
        if(confirmPassword == password){
            return true
        }
        return false
    }

    fun onSignUpClick(openAndPopUp: (String, String)-> Unit)  {
        signUpInProgress.value = true
        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(AppText.email_error)

            return
        }

//        if (!password.isValidPassword()) {
////            SnackbarManager.showMessage(AppText.password_error)
//            HandleException(null, "Password is not valid")
//            return
//        }

        if (!password.passwordMatches(confirmPassword)) {
//            SnackbarManager.showMessage(AppText.password_match_error)

            return
        }

        viewModelScope.launch {

            accountService.signup(username,email, password, handleException = { exception, msg ->
                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(message)
            }){//success callback
                 exception, msg ->

                viewModelScope.launch {
                    val mapData = mapOf(
                        "userName" to username,
                        "email" to email
                    )
                    firestoreService.createOrUpdateMyUserProfile(mapData, handleException = {exception, msg ->
                        exception?.printStackTrace()
                        val errorMsg = exception?.localizedMessage ?: ""
                        val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                        onPopupNotificationChange(message)
                    }, handleSuccess = {exception, msg ->
                        exception?.printStackTrace()
                        val errorMsg = exception?.localizedMessage ?: ""
                        val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                        onPopupNotificationChange(message)
                    })
                }


                exception?.printStackTrace()
                val errorMsg = exception?.localizedMessage ?: ""
                val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                onPopupNotificationChange(popMsg = message)

                //call profile edit screen
                openAndPopUp("LoginAuthScreen","SignUpScreen")
            }

//            openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
        }
    }

    fun onLoginScreenButtonClick(openAndPopUp: (String, String) -> Unit){
        openAndPopUp("LoginAuthScreen","SignUpScreen")
    }

    private fun onPopupNotificationChange(popMsg: String) {
       poppy.value = Event(popMsg)
        signUpInProgress.value = false
    }
}
