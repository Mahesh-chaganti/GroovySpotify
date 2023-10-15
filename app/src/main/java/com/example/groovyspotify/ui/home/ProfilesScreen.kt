package com.example.groovyspotify.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.common.composable.ExtraBoldText
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation
import com.example.groovyspotify.ui.swipe.MatchProfile
import font.helveticaFamily

@Composable
fun ProfilesScreen(
    modifier: Modifier,
    matchProfile: UserProfile,
    isPlaying: () -> Boolean,
    onPauseToggle: () -> Unit
) {
    val isPlaying = remember(isPlaying()) { isPlaying() }




    Box(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(750.dp)

            .border(shape = RoundedCornerShape(8.dp), color = Color.White, width = 2.dp)


    ) {
        AsyncImage(
            model = matchProfile.profilePhotoUrl,
            contentDescription = "My Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .border(shape = RoundedCornerShape(8.dp), color = Color.Unspecified, width = 1.dp),
            alignment = Alignment.Center
        )
        Column(modifier = Modifier.align(Alignment.BottomCenter), horizontalAlignment = Alignment.Start) {
            ExtraBoldText(text = matchProfile.name + " ${matchProfile.age}", modifier = Modifier)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Card(
                    modifier = Modifier
                        .size(128.dp)
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color.White),
                    elevation = 8.dp,
                    backgroundColor = Color.Gray,

                    ) {

                    AsyncImage(
                        model = matchProfile.track.images[0].url ?: "",
                        contentDescription = "My album art",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                    IconButton(

                        onClick = {

                            onPauseToggle.invoke()
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            painter =
                            if (isPlaying) {
                                painterResource(id = R.drawable.round_pause_24)
                            } else {
                                painterResource(id = R.drawable.round_play_arrow_24)
                            },
                            contentDescription = "Play/Pause",
                            tint = Color.White
                        )
                    }

                }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {


                    CommonText(
                        modifier = Modifier.padding(2.dp),
                        text = matchProfile.track.name ?: "",

                        )


                    CommonText(
                        modifier = Modifier.padding(2.dp),
                        text = matchProfile.track.artists[0].name ?: "",

                        )

                    ExoplayerAnimation(
                        isAnimating = isPlaying,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(2.dp)
                    )
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun ProfilesScreenPreview() {
//    ProfilesScreen()
//}