package com.example.groovyspotify.ui.auth

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.common.ext.isValidEmail
import com.example.groovyspotify.model.firestore.ChatData
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.services.AccountService
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.services.common.HandleException
import com.example.groovyspotify.ui.ParentViewModel
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService
) : ParentViewModel(logService) {
    var userGetting = mutableStateOf(false)
        private set
    var signedIn = mutableStateOf(false)
        private set

    var loginInProgress = mutableStateOf(false)
        private set

    var poppy = mutableStateOf<Event<String>?>(null)
        private set


    var uiState = mutableStateOf(LoginUiState())
        private set

    var profilesInProgress = mutableStateOf(false)
        private set

    var userDataState = mutableStateOf(UserProfile())
        private set

    var logoutInProgress = mutableStateOf(false)
        private set



    private val _chatListStateFlow = MutableStateFlow<List<ChatData>>(listOf())
    val chatListStateFlow: StateFlow<List<ChatData>> = _chatListStateFlow


    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    private val _userProfilesStateFlow = MutableStateFlow<List<UserProfile>>(listOf())
    val userProfilesStateFlow: StateFlow<List<UserProfile>> = _userProfilesStateFlow


    init {

        signedIn.value = accountService.currentUser != null
        getMyUserFun()

    }

    fun getMyUserFun(){
        userGetting.value = true
        viewModelScope.launch {

            accountService.currentUserId?.let {


                firestoreService.getMyUserProfile(it, handleException = { exception, msg ->
                    exception?.printStackTrace()
                    val errorMsg = exception?.localizedMessage ?: ""
                    val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                    onPopupNotificationChange(message)
                }) { user, msg ->

                    onPopupNotificationChange(msg)
                    userDataState.value = user
                    populateCardsFun()
                    populateChatsFun()



                }
            }



        }
    }
    fun populateChatsFun(){

        viewModelScope.launch {


            firestoreService.populateChats(
                handleException = { exception, msg ->
                    exception?.printStackTrace()
                    val errorMsg = exception?.localizedMessage ?: ""
                    val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                    onPopupNotificationChange(message)

                }
            ) { listOfChats ->

              _chatListStateFlow.value = listOfChats

            }
        }
    }

    fun populateCardsFun(){
        profilesInProgress.value = true
        viewModelScope.launch {


            firestoreService.populateCards(
                handleException = { exception, msg ->
                    exception?.printStackTrace()
                    val errorMsg = exception?.localizedMessage ?: ""
                    val message = if (msg.isEmpty()) errorMsg else "$msg: $errorMsg"
                    onPopupNotificationChange(message)

                }
            ) { profiles, msg ->
                onPopupNotificationChange(msg)
                _userProfilesStateFlow.value = profiles



            }

            Log.d("ExceptionalViewMOdel", "${_userProfilesStateFlow.value} ")
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
                signedIn.value = true

                onPopupNotificationChange(popMsg = message)


                //call profiles screen
//                if(userDataState.value != null){
//                    if(userDataState.value.favArtists.isNullOrEmpty() || userDataState.value.favLanguages.isNullOrEmpty()){
//                        openAndPopUp("ProfileScreenLanguagesAndArtists","LoginAuthScreen")
//                    }
//
//
//                }
                getMyUserFun()
                

            }

        }

    }
    fun onSignUpScreenButtonClick(openAndPopUp: (String, String) -> Unit){
        openAndPopUp("SignUpScreen","LoginAuthScreen")
    }

    private fun onPopupNotificationChange(popMsg: String) {
        poppy.value = Event(popMsg)

        loginInProgress.value = false
        logoutInProgress.value = false
        userGetting.value = false
        profilesInProgress.value = false

    }
    fun signout(onSuccess:() -> Unit ){
        logoutInProgress.value = true
        viewModelScope.launch {
            try {
                accountService.logout()
                signedIn.value = false
                onSuccess()
                onPopupNotificationChange("Logout success")
                uiState.value = LoginUiState()
                
            }catch (e: Exception){
                e.message?.let { onPopupNotificationChange(it )}
            }
        }


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