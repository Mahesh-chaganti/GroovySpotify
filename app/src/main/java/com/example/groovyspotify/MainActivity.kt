package com.example.groovyspotify

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.groovyspotify.ui.auth.LoginViewModel
import com.example.groovyspotify.ui.theme.GroovySpotifyTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.groovyspotify.navigation.NavigationScreen
import com.example.groovyspotify.ui.ParentViewModel
import com.example.groovyspotify.ui.auth.SignUpViewModel
import com.example.groovyspotify.ui.fcm.FCMViewModel
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.profilescreens.audio.FeaturedAudioViewModel
import com.example.groovyspotify.ui.profilescreens.languagesandartists.LanguagesAndArtistsViewModel
import com.example.groovyspotify.ui.profilescreens.loadContacts
import com.example.groovyspotify.ui.realtimedatabase.RealtimeDatabaseViewModel
//import com.example.groovyspotify.ui.profilescreens.loadImageFromUri
//import com.example.groovyspotify.ui.profilescreens.openGallery
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by  viewModels<LoginViewModel>()
    private val spotifyAuthViewModel by  viewModels<SpotifyApiViewModel>()
//    private val navEliminationViewModel by viewModels<NavEliminationViewModel>()
    private val firestoreViewModel by viewModels<FirestoreViewModel>()
    private val fcmViewModel by viewModels<FCMViewModel>()
    private val realtimeDatabaseViewModel by viewModels<RealtimeDatabaseViewModel>()
    private val signupViewModel by  viewModels<SignUpViewModel>()
    private val languagesAndArtistsViewModel by  viewModels<LanguagesAndArtistsViewModel>()
    private val featuredAudioViewModel by  viewModels<FeaturedAudioViewModel>()
    private val parentViewModel by viewModels<ParentViewModel>()








    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme




                val requestPermissionLaunchers = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { mapOfPermissions ->
                        if (mapOfPermissions["Manifest.permission.READ_CONTACTS"] == true) {
                            // Permission granted, perform contact synchronization
                            loadContacts(context = this)
                        }
                        else if(mapOfPermissions["Manifest.permission.ACCESS_COARSE_LOCATION"] == true){

                        }
                        else if(mapOfPermissions["Manifest.permission.POST_NOTIFICATIONS"] == true){

                        }


                    }
                )
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_CONTACTS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission already granted
                    // Call a function to use the contacts
                    loadContacts(this)
                } else {
                    // Permission not granted, request it
                    LaunchedEffect(key1 = Unit ){
                        requestPermissionLaunchers.launch(arrayOf(Manifest.permission.READ_CONTACTS))

                    }
                }






                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission already granted
                    // Call a function to use the Location


                } else {
                    // Permission not granted, request it
                    LaunchedEffect(key1 = Unit ){
                        requestPermissionLaunchers.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))

                    }
                }
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission already granted



                } else {
                    // Permission not granted, request it
                    LaunchedEffect(key1 = Unit ){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                            requestPermissionLaunchers.launch(arrayOf( Manifest.permission.POST_NOTIFICATIONS))
                        }
                    }
                }


                fcmViewModel.getFCMToken()





                NavigationScreen(
                    viewModel = viewModel,
                    spotifyApiViewModel = spotifyAuthViewModel,
//                    navEliminationViewModel = navEliminationViewModel,
                    firestoreViewModel= firestoreViewModel,
                    fcmViewModel = fcmViewModel,
                    realtimeDatabaseViewModel = realtimeDatabaseViewModel,
                    signUpViewModel = signupViewModel,
                    featuredAudioViewModel = featuredAudioViewModel,
                    languagesAndArtistsViewModel = languagesAndArtistsViewModel,
                    parentViewModel = parentViewModel
                )


            }



    }




}

@Composable
fun MainScaffold() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Scaffold(
            backgroundColor = Color.Black,
            topBar = {
                // Add top app bar content here
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    title = { Text(text = "My Screen Title") },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle back navigation */ }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle settings click */ }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    },


                )
            },


            bottomBar = {
                // Add bottom navigation bar content here
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    elevation = 8.dp,
                    content = {
                        Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        IconButton(onClick = { /* Handle home click */                       }) {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                        }
                        IconButton(onClick = { /* Handle search click */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = { /* Handle favorite click */ }) {
                            Icon(Icons.Default.Favorite, contentDescription = "Favorite")
                        }
                        IconButton(onClick = { /* Handle profile click */ }) {
                            Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                        }
                    }
                    })
            },
            floatingActionButton = {
                // Add floating action button content here

            },
            content = {
                // Add main content here
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(it)) {
                    Image(painter = painterResource(id = R.drawable.image), contentDescription =null)
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GroovySpotifyTheme {
        MainScaffold()
    }
}