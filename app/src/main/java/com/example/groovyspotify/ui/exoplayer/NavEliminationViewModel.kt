package com.example.groovyspotify.ui.exoplayer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavEliminationViewModel @Inject constructor(): ViewModel(){
    var myData by mutableStateOf<TrackResponse?>(null)

    fun setTrackData(data: TrackResponse) {
        myData = data
    }
    fun selectedFeaturedAudio(data: TrackResponse){
        myData = data
    }
}