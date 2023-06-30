package com.example.groovyspotify.ui.home

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
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
import font.helveticaFamily

@Composable
fun MusicRoomsScreen() {
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
                .clickable {  },
            imageVector = Icons.Filled.Notifications,
            contentDescription = "Back button",
            tint = Color.White
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter).padding(8.dp),
            text = "Musicrooms",
            fontSize = 36.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Column(modifier = Modifier
            .fillMaxSize().padding(top = 50.dp).padding(16.dp)
        ) {



            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                items(10) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable {  },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {



                            AsyncImage(
                                modifier = Modifier.size(48.dp)
                                    .background(shape = CircleShape, color = Color.White)
                                    .padding(8.dp),
                                model = R.drawable.image,
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
                                text = "User $it",
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



@Preview
@Composable
fun MusicScreensPreview() {
    MusicRoomsScreen()
}