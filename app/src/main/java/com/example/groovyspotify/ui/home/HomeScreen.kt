package com.example.groovyspotify.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import font.helveticaFamily
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun HomeScreen(firestoreViewModel: FirestoreViewModel) {
    var mapOfProfiles = firestoreViewModel?.mapOfEachProfileArtists?.collectAsState()
    var profileName by remember{ mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Log.d("userflow", "HomeScreen: $mapOfProfiles")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black

            )
    )
    {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 128.dp),
            text = "Discover",
            fontSize = 36.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 128.dp),
            text = profileName,
            fontSize = 36.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Button(modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                mapOfProfiles!!.value!!.let {

                    it.forEach {

                        it.forEach {
                            var max = Double.MIN_VALUE
                            var maxKey = ""
                            var similarityValue = calculateJaccardSimilarity(
                                firestoreViewModel.favoriteArtists,
                                it.value
                            )
                            val mapOfUsers = mutableMapOf<String, Double>()
                            mapOfUsers[it.key] = similarityValue
                            for ((key, value) in mapOfUsers) {
                                if (value > max) {
                                    max = value
                                    maxKey = key
                                }
                            }
                            profileName = maxKey
                            Log.d("Similar", "HomeScreen: $mapOfUsers")

//                            if (similarityValue > max) {
//                                max = similarityValue
//                                profileName = it.key
//
//
//                            }
                        }
                    }
                }
        }) {
            Text(
                modifier = Modifier

                    .padding(vertical = 28.dp),
                text = "Surprise me",
                fontSize = 36.sp,
                fontFamily = helveticaFamily,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }





}

fun calculateJaccardSimilarity(listA: List<String>, listB: List<String>): Double {
    val setA = listA.toSet()
    val setB = listB.toSet()

    val intersectionSize = setA.intersect(setB).size
    val unionSize = setA.union(setB).size

    return intersectionSize.toDouble() / unionSize.toDouble()
}

//val userALanguages = listOf("Telugu", "Hindi", "English")
//val userAartists = listOf("Selena", "Justin", "Ava", "Maroon 5", "DSP")
//
//val userBLanguages = listOf("Telugu", "English", "Tamil")
//val userBartists = listOf("Justin", "Maroon 5", "Taylor Swift")
//
//val languageSimilarity = calculateJaccardSimilarity(userALanguages, userBLanguages)
//val artistSimilarity = calculateJaccardSimilarity(userAartists, userBartists)

fun similarValues(mapOfUsers :MutableMap<String, Double>){

}