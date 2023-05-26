package com.example.groovyspotify.model.profile

data class MusicLanguage(
    val language: String
)

val listOfMusicLanguages = listOf<MusicLanguage>(
    MusicLanguage("Telugu"),
    MusicLanguage("Hindi"),
    MusicLanguage("English")
)