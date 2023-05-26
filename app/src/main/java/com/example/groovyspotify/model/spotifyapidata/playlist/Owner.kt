package com.example.groovyspotify.model.spotifyapidata.playlist

import com.example.groovyspotify.model.spotifyapidata.track.ExternalUrls

data class Owner(
    val display_name: String,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)