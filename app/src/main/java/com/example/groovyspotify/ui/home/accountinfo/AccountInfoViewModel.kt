package com.example.groovyspotify.ui.home.accountinfo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountInfoViewModel @Inject constructor():ViewModel(){
    fun onLogoutClick(openAndPopUpLoginScreen:(String,String) -> Unit){
        openAndPopUpLoginScreen("LoginAuthScreen","LoginAuthScreen")
    }
    fun onProfilePictureEditClick(openGenderAndDob:(String) -> Unit){
        openGenderAndDob("GenderAndDobScreen")
    }
    fun onTrackEditClick(openSearch:(String)->Unit){
        openSearch("ProfileFeaturedAudio")
    }
    fun onArtistsEditClick(openLanguagesAndArtists:(String) -> Unit){
        openLanguagesAndArtists("ProfileScreenLanguagesAndArtists")
    }
    fun onLanguagesEditClick(openLanguagesAndArtists:(String) ->Unit){
        openLanguagesAndArtists("ProfileScreenLanguagesAndArtists")
    }

    fun onSearchClick(openSearch:(String) -> Unit) {
        openSearch("ProfileFeaturedAudio")
    }
}