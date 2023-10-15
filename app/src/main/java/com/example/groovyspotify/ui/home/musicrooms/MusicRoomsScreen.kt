package com.example.groovyspotify.ui.home.musicrooms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.ui.auth.LoginViewModel
import com.example.groovyspotify.ui.swipe.SwipeScreenViewModel
import font.helveticaFamily

@Composable
fun MusicRoomsScreen(loginViewModel: LoginViewModel) {
    val chatList by loginViewModel.chatListStateFlow.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black

            )
    )
    {
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(18.dp)
                .size(24.dp)
                .clickable { },
            imageVector = Icons.Filled.Notifications,
            contentDescription = "Back button",
            tint = Color.White
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(8.dp),
            text = "Musicrooms",
            fontSize = 36.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
        ) {



            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                itemsIndexed(chatList) {index, item ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable { },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {



                            AsyncImage(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(shape = CircleShape, color = Color.White)
                                    .padding(8.dp),
                                model = item.user2.imageUrl,
                                contentDescription = "user",
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,

                                )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(2.dp),
                                text = item.user2.name,
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                modifier = Modifier.padding(2.dp),
                                text = "message",
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }

                    }
                }

            }

        }
    }
}



//@Preview
//@Composable
//fun MusicScreensPreview() {
////    MusicRoomsScreen()
//}