package com.example.groovyspotify.ui.home

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.ui.profilescreens.audio.AlbumRow
import com.example.groovyspotify.ui.profilescreens.audio.FeaturedAudioViewModel
import com.example.groovyspotify.ui.profilescreens.audio.OneTimeLazyRow
import com.example.groovyspotify.ui.profilescreens.audio.TrackRow
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import font.helveticaFamily

@Composable
fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        CommonText(text = "Search", modifier = Modifier.align(Alignment.Center))
    }
}
//@Composable
//fun SearchScreen(spotifyApiViewModel: SpotifyApiViewModel?,navController: NavController,featuredAudioViewModel: FeaturedAudioViewModel) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Color.Black
//
//            )
//    )
//    {
//
//
//
//        var searchQuery by remember { mutableStateOf("") }
//        val tapState = spotifyApiViewModel?.tapState!!.collectAsState()
////        val accessTokenState = spotifyApiViewModel?.accessTokenResponse?.collectAsState()
//        var accessToken by remember { mutableStateOf("") }
//
////        accessTokenState?.value.let {
////            when (it) {
////                is Resource.Failure -> {
////                    val context = LocalContext.current
////                    Toast.makeText(context, it.exception.message + "Token", Toast.LENGTH_LONG).show()
////                }
////
////                Resource.Loading -> {
////                    CircularDotsAnimation()
////                }
////
////                is Resource.Success -> {
////
////                    accessToken = it.data.accessToken
////                }
////
////                else -> {}
////            }
////        }
//        Column() {
//
//
//            Column(
//                modifier = Modifier
//                    .padding(horizontal = 32.dp)
//                    ,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                OutlinedTextField(
//                    value = searchQuery,
//                    onValueChange = { newQuery ->
//                        searchQuery = newQuery
////                        spotifyApiViewModel!!.getSearchTAPData(
////                            query = searchQuery,
////                            type = "album,track,playlist",
////                            limit = 10,
////                            market = "IN",
////                            authorization = "Bearer $accessToken"
////                        )
//                    },
//                    label = {
//                        Text(
//                            text = "Search for a song",
//                            fontSize = 18.sp,
//                            fontFamily = helveticaFamily,
//                            fontStyle = FontStyle.Normal,
//                            fontWeight = FontWeight.Medium,
//                            color = Color.White
//                        )
//                    },
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        keyboardType = KeyboardType.Text
//                    ),
//
//                    shape = RoundedCornerShape(16.dp),
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        cursorColor = Color.White,
//                        textColor = Color.White,
//                        backgroundColor = Color.DarkGray,
//                        focusedBorderColor = Color(0xFF0890CD),
//                        unfocusedBorderColor = Color.White,
//                        disabledTextColor = Color.White,
//                        focusedLabelColor = Color(0xFF0890CD),
//                        placeholderColor = Color.White
//
//                    ),
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//
//
//
//            tapState?.value.let {
//                when (it) {
//                    is Resource.Failure -> {
//                        val context = LocalContext.current
//                        Toast.makeText(
//                            context,
//                            it.exception.message + "TAP call",
//                            Toast.LENGTH_LONG
//                        )
//                            .show()
//                    }
//
//                    Resource.Loading -> {
//                        CircularDotsAnimation()
//                    }
//
//                    is Resource.Success -> {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(8.dp)
//                        ) {
//
//
//                            LazyColumn() {
//                                itemsIndexed(it.data.tracks.items) { index, item ->
//                                    TrackRow(
//
//                                        track = item,
//
//                                    ){}
//                                    Spacer(modifier = Modifier.height(8.dp))
//                                }
//                                itemsIndexed(it.data.albums.items) { index, item ->
//                                    Column() {
//                                        Spacer(modifier = Modifier.height(8.dp))
//
//                                        AlbumRow(album = item){}
//                                        Spacer(modifier = Modifier.height(8.dp))
//                                        if (index == it.data.albums.items.size - 1) {
//                                            OneTimeLazyRow(items = it.data.playlists.items, featuredAudioViewModel = featuredAudioViewModel)
//                                        }
//
//                                    }
//                                }
//
//
//                            }
//
//                        }
//                    }
//
//                    else -> {}
//                }
//            }
//        }
//    }
//}
//
//
//
