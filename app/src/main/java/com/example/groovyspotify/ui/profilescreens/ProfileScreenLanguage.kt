package com.example.groovyspotify.ui.profilescreens

import android.util.Base64
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.R
import com.example.groovyspotify.data.utils.SpotifyConstant
import com.example.groovyspotify.model.profile.MusicLanguage
import com.example.groovyspotify.model.profile.ProfileArtist
import com.example.groovyspotify.model.profile.listOfMusicLanguages
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import font.helveticaFamily
import kotlinx.coroutines.launch

@Composable
fun ProfileScreenLanguage(spotifyApiViewModel: SpotifyApiViewModel?,navController: NavController) {
    val clientId = SpotifyConstant.clientId // Your client id
    val clientSecret = SpotifyConstant.clientSecret// Your secret
    val scope = rememberCoroutineScope()
    val authHeader = "Basic " + Base64.encodeToString("$clientId:$clientSecret".toByteArray(), Base64.NO_WRAP)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black
//                brush =
                //                Brush.verticalGradient(
//                    colors = listOf(
//
//                        Color(0xFF000000),
//                        Color(0xFFFFFFFF)
//                    )
//                )
            )
    )
    {

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
            text = "Please select your preferred languages",
            fontSize = 32.sp,
            color = Color.White,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp)
                .padding(top = 50.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            LazyRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(listOfMusicLanguages) { index, item ->
                    UniversalButton(
                        modifier = Modifier.wrapContentSize(),
                        itemLanguage = item,
                    )
                }
            }

        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            onClick = {
                navController.navigate("ProfileScreenArtist")
                scope.launch {
                    try {
                        spotifyApiViewModel!!.getAccessToken(authHeader)



                    } catch (e: Exception) {
                        // Handle network or API-related errors here
                        Log.d("ClientAccessTokenCall", "ClientCredentialsAuthScreen: $e")
                    }
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
                contentDescription = "Next"
            )
        }
    }
}

@Composable
fun UniversalButton(modifier: Modifier, itemLanguage: MusicLanguage?) {
    var colorChange by remember { mutableStateOf(false) }


    Button(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        onClick = {
            colorChange = !colorChange

        },
        colors = ButtonDefaults
            .buttonColors(

                backgroundColor = if (colorChange) Color.White else Color.DarkGray,
                contentColor = if (colorChange) Color.DarkGray else Color.White


            )
    ) {
        Text(
            text = itemLanguage!!.language,
            fontSize = 16.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview
@Composable
fun ButtonPreview() {
    ProfileScreenLanguage(spotifyApiViewModel = null,rememberNavController())
}
