package com.example.groovyspotify.ui.profilescreens.languagesandartists

import com.example.groovyspotify.model.profile.ProfileArtist

data class LangAndArtistsUiState(
    val listOfArtists: MutableList<ProfileArtist> = mutableListOf(),
    val listOfLanguages: MutableList<String> = mutableListOf()
)



