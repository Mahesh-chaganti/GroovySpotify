package com.example.groovyspotify.ui.home

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.firestore.Contact
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.ui.fcm.FCMViewModel
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.google.gson.Gson
import font.helveticaFamily
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun HomeScreen(
    navController: NavController,
    fcmViewModel: FCMViewModel,
    firestoreViewModel: FirestoreViewModel
) {
    var filteredUserProfiles by remember { mutableStateOf(ArrayList<UserProfile>()) }
    var listOfProfiles = firestoreViewModel?.listOfRecommendedProfiles?.collectAsState()
    var profileName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val myUserProfile = firestoreViewModel.myUserProfile.collectAsState()
    Log.d("userflow", "HomeScreen: $listOfProfiles")
    var filteredUsernames: Map<String, Double> = mapOf()
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
                .padding(top = 18.dp),
            text = "Discover",
            fontSize = 36.sp,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

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


        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        )
        {


            listOfProfiles?.value.let {
                when (it) {
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                    }

                    Resource.Loading -> {
                        CircularDotsAnimation()
                    }

                    is Resource.Success -> {


                        val mapOfProfileSimilarities = mutableMapOf<String, Double>()
                        it.data.forEach {

                            myUserProfile.value.let { mine ->
                                when (mine) {
                                    is Resource.Failure -> {
                                        val context = LocalContext.current
                                        Toast.makeText(
                                            context,
                                            mine.exception.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    Resource.Loading -> {
                                        CircularDotsAnimation()
                                    }

                                    is Resource.Success -> {
                                        val similarityValue = calculateJaccardSimilarity(
                                            mine.data.favoriteArtists,
                                            it.favoriteArtists as List<String>
                                        )
                                        mapOfProfileSimilarities[it.userName] = similarityValue
                                        Log.d(
                                            "userSuggestedMapSimilar",
                                            "HomeScreen: $mapOfProfileSimilarities"
                                        )

                                        filteredUsernames =
                                            mapOfProfileSimilarities.filterValues { value -> value > 0.4 }
                                        Log.d(
                                            "userSuggestedNames",
                                            "HomeScreen: $filteredUsernames"
                                        )
                                        fcmViewModel.fcmMessagingToken()

                                    }

                                    else -> {}
                                }


                            }

                        }


                    }


                    else -> {}
                }

            }


            listOfProfiles?.value.let {
                when (it) {
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                    }

                    Resource.Loading -> {
                        CircularDotsAnimation()
                    }

                    is Resource.Success -> {
                        filteredUserProfiles = it.data.filter { userProfile ->
                            val username = userProfile.userName
                            filteredUsernames.containsKey(username)
                        } as ArrayList<UserProfile>
                        UserProfileList(
                            userProfiles = filteredUserProfiles,
                            navController = navController
                        )
                        Log.d("userSuggested", "HomeScreen: $filteredUserProfiles")
                    }

                    else -> {}
                }
            }

//            else {
//                navController.navigate("LoginAuthScreen")
//            }
        }
    }
    Log.d("userSuggested2", "HomeScreen: $filteredUserProfiles")

}

@Composable
fun UserProfileList(userProfiles: List<UserProfile>, navController: NavController) {
    var clickedProfile by remember {
        mutableStateOf(false)
    }

    LazyColumn {
        itemsIndexed(userProfiles) { index: Int, item: UserProfile ->

            val json = Uri.encode(Gson().toJson(item))
            Log.d("userdata", "UserProfileList: $json")
            ReUsableProfileCard(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.navigate("ProfileCard/true/$json")
                    },
                userProfile = item
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

