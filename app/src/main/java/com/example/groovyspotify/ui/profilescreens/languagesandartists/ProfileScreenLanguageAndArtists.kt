package com.example.groovyspotify.ui.profilescreens.languagesandartists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import com.example.groovyspotify.common.composable.ColoredText
import com.example.groovyspotify.common.composable.CommonProgressSpinner
import com.example.groovyspotify.common.composable.ExtraBoldText
import com.example.groovyspotify.common.composable.UniversalButton
import com.example.groovyspotify.model.profile.ProfileArtist
import com.example.groovyspotify.model.profile.listOfEnglishArtists
import com.example.groovyspotify.model.profile.listOfHindiArtists
import com.example.groovyspotify.model.profile.listOfMusicLanguages
import com.example.groovyspotify.model.profile.listOfTeluguArtists

@Composable
fun ProfileScreenLanguagesAndArtists(
    languagesAndArtistsViewModel: LanguagesAndArtistsViewModel,
    openAndPopUp: (String, String)->Unit
) {
    val uiState by languagesAndArtistsViewModel.uiState
    val loading by languagesAndArtistsViewModel.updateInProgress
    val scope = rememberCoroutineScope()
    var favArtistsEmpty by remember {
        mutableStateOf(false)
    }
//    val mapData = mapOf(
//        "myLanguages" to myLanguages
//    )

    val myLanguages = remember { mutableListOf<String>() }
    val favoriteArtists = remember { mutableListOf<ProfileArtist>() }
    val newList = listOfEnglishArtists + listOfHindiArtists + listOfTeluguArtists
    val randomSubset = newList.chunked(5)
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
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
                .padding(top = 20.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            ExtraBoldText(text = "Select your preferred languages"  , modifier =Modifier

                .padding(start = 10.dp, top = 8.dp) )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(listOfMusicLanguages) { index, item ->

                    UniversalButton(
                        modifier = Modifier.wrapContentSize(),
                        data = item,)
                    { languagesAndArtistsViewModel.addLanguage(item) }

                }
            }

            ExtraBoldText(text = "Select your preferred Artists"  , modifier =Modifier.padding(start = 10.dp, top = 8.dp)
                )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
                    .padding(bottom = 40.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                itemsIndexed(randomSubset) {index, items ->


                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),

                        ) {

                        itemsIndexed(items) { index, item ->
                            UniversalButton(
                                modifier = Modifier
                                    .wrapContentHeight(Alignment.CenterVertically)
                                    .padding(horizontal = 4.dp),

                                data = item.name,

                            ){
                                languagesAndArtistsViewModel.addArtist(artist = item)
                            }
                    }
                }
            }

            }
        }

        ColoredText(
            text = "Next >",
            color = Color(0xFFFF5722),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            //send language and artist data
            // navigate to next screen
                languagesAndArtistsViewModel.onNextClick(openAndPopUp)

        }
        if(loading)
            CommonProgressSpinner()
    }

}


//@Preview
//@Composable
//fun ButtonPreview() {
//    ProfileScreenLanguageAndArtists(
//
//    )
//}
