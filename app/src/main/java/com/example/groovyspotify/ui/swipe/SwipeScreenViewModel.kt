package com.example.groovyspotify.ui.swipe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.model.firestore.COLLECTION_NAMES.COLLECTION_PROFILES
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.services.AccountService
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.common.Event
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwipeScreenViewModel @Inject constructor(
   private val firestoreService: FirestoreService,
    private val accountService: AccountService
) : ViewModel() {
    var poppy = mutableStateOf<Event<String>?>(null)
        private set
    var inProgressProfiles = mutableStateOf(false)
        private set

    var userDataState = mutableStateOf(UserProfile())
        private set

    fun getMyUserFun(){
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




                }
            }



        }
    }

    fun onLike(selectedUser: UserProfile) {
        viewModelScope.launch {
            firestoreService.onLike(
                selectedUser = selectedUser,
                handleException = {}
            ) {selectedUser ->
                getMyUserFun()

                onPopupNotificationChange(popMsg = "Its a Match")
                addToChats(user1 = userDataState.value, user2 = selectedUser)
            }
        }

    }
    fun addToChats(user1: UserProfile,user2: UserProfile){
        viewModelScope.launch{
            firestoreService.addToChats(user1 = user1,user2 = user2)
        }
    }

    private fun onPopupNotificationChange(popMsg: String) {
        poppy.value = Event(popMsg)
        inProgressProfiles.value = false
    }

    fun onDislike(selectedUser: UserProfile) {
        viewModelScope.launch {
            firestoreService.onDislike(selectedUser)
        }
    }




}

