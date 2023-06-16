package com.example.groovyspotify.ui.profilescreens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.R
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.profile.ProfileArtist
import com.example.groovyspotify.model.profile.listOfEnglishArtists
import com.example.groovyspotify.model.profile.listOfHindiArtists
import com.example.groovyspotify.model.profile.listOfTeluguArtists
import com.example.groovyspotify.ui.home.CircularDotsAnimation
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import font.helveticaFamily
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreenArtist(spotifyApiViewModel: SpotifyApiViewModel?, firestoreViewModel: FirestoreViewModel?,navController: NavController) {
    val random = Random
    val scope = rememberCoroutineScope()

    var myUserProfile = firestoreViewModel?.myUserProfile?.collectAsState()
    var userName by remember {
        mutableStateOf("")
    }
    var colorChange by remember { mutableStateOf(false) }
    val newList = listOfEnglishArtists + listOfHindiArtists + listOfTeluguArtists
    val randomSubset = newList.chunked(5)
    val favoriteArtists by remember { mutableStateOf(ArrayList<String>()) }
    var featuredAudioEmpty by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//
//                        Color(0xFF000000),
//                        Color(0xFFFFFFFF)
//                    )
//                )
            )
    ) {

        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(18.dp)
                .size(24.dp)
                .clickable { navController.popBackStack() },
            painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
            contentDescription = "Back button",
            tint = Color(0xFFFF5722)
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 60.dp, top = 8.dp),
            text = "Please select your favourite artists",
            fontSize = 32.sp,
            color = Color.White,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold
        )


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(top = 50.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

            ) {
            itemsIndexed(randomSubset) {index, items ->


                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),

                ) {

                    itemsIndexed(items) { index, item ->
                        ButtonArtist(
                            modifier = Modifier
                                .wrapContentHeight(Alignment.CenterVertically)
                                .padding(horizontal = 4.dp),

                            itemArtist = item,
                            onClick = {
                                colorChange = !colorChange
                                favoriteArtists.add(item.name)},
                            colorChange = colorChange
                        )
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomEnd)
                .padding(40.dp),
            shape = RoundedCornerShape(24.dp),
            onClick = {




                val mapData = mapOf(
                    "favoriteArtists" to favoriteArtists)

                scope.launch {
                    firestoreViewModel?.updateUserProfile(
                        userName = userName ,
                        mapData = mapData
                    )
                }
                if(featuredAudioEmpty){
                    navController.navigate("ProfileFeaturedAudio")
                }
                else{
                    navController.navigate("MainHomeScreen")
                }

            },
            colors = ButtonDefaults
                .buttonColors(

                    backgroundColor = Color(0xFFFF3A20),
                    contentColor = Color.White


                )
        ) {
            Text(
                text = "Next",
                fontSize = 16.sp,
                fontFamily = helveticaFamily,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Medium
            )
            Icon(
                painter = painterResource(id = R.drawable.round_navigate_next_24),
                contentDescription = "Next Screen"
            )
        }
    }
    myUserProfile?.value.let {
        when (it) {
            is Resource.Failure -> {
                val context = LocalContext.current
                Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
            }

            Resource.Loading -> {
                CircularDotsAnimation()
            }

            is Resource.Success -> {
                userName = it.data.userName
                if( it.data.featuredAudio.isNullOrBlank()){
                    featuredAudioEmpty = true
                }

            }

            else -> {}
        }


    }
    
}


@Composable
fun ButtonArtist(modifier: Modifier, itemArtist: ProfileArtist?,onClick: () -> Unit, colorChange: Boolean) {



    Button(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        onClick = {

            onClick.invoke()
        },
        colors = ButtonDefaults
            .buttonColors(

                backgroundColor = if (colorChange) Color.White else Color.DarkGray,
                contentColor = if (colorChange) Color.DarkGray else Color.White


            )
    ) {
        Text(
            text = itemArtist!!.name,
            fontSize = 16.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium
        )
    }

}

@Preview
@Composable
fun ProfileScreenArtistPreview() {
    ProfileScreenArtist(spotifyApiViewModel = null, firestoreViewModel = null,rememberNavController())
}

