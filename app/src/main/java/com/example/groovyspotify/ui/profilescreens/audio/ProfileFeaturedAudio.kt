package com.example.groovyspotify.ui.profilescreens.audio


import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.groovyspotify.common.composable.BasicFieldComposable
import com.example.groovyspotify.common.composable.CommonProgressSpinner
import com.example.groovyspotify.common.composable.ExtraBoldText
import com.example.groovyspotify.common.composable.displayToast
import com.example.groovyspotify.ui.exoplayer.ExoplayerImpl

import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import com.google.android.gms.common.internal.service.Common

import com.example.groovyspotify.R.string as AppText


@Composable
fun ProfileFeaturedAudio(

    featuredAudioViewModel: FeaturedAudioViewModel,
    navController: NavController

) {
    val uiState by featuredAudioViewModel.uiState
    val toastMsgEvent by featuredAudioViewModel.poppy
val loading by featuredAudioViewModel.inProgress

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black

            )
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .align(Alignment.TopStart)
        ) {
            ExtraBoldText(
            text = stringResource(id = AppText.featured_audio),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, top = 8.dp)

        )

            BasicFieldComposable(
                text = AppText.featured_audio,
                value = uiState.searchQuery,
                onNewValue = featuredAudioViewModel::onSearchValueChange,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
           LazyColumn() {
                itemsIndexed(uiState.tapData.tracks.items) { index, item ->
                    TrackRow(track = item, navController = navController,trackClick = {
                        featuredAudioViewModel.onTrackClick(item)
                    },
                    )
                 ExoplayerDialog(featuredAudioViewModel = featuredAudioViewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                itemsIndexed(uiState.tapData.albums.items) { index, item ->
                    Column() {
                        Spacer(modifier = Modifier.height(8.dp))

                        AlbumRow(album = item,
                            navController = navController)
                        Spacer(modifier = Modifier.height(8.dp))
                        if (index == uiState.tapData.albums.items.size - 1) {
                            OneTimeLazyRow(
                                items = uiState.tapData.playlists.items,
                                navController = navController
                            )
                        }

                    }
                }
            }
            toastMsgEvent?.let { displayToast(toastMsgEvent = it) }
        }
        if(loading)
            CommonProgressSpinner()
    }
}


//@Preview
//@Composable
//fun ProfileFeaturedAudioPreview() {
//    ProfileFeaturedAudio(
////        spotifyApiViewModel = null,
////        navEliminationViewModel = null,
////        navController = rememberNavController()
//    )
//}