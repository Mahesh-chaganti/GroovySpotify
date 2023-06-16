package com.example.groovyspotify

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.theme.GroovySpotifyTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.groovyspotify.navigation.NavigationScreen
import com.example.groovyspotify.ui.exoplayer.NavEliminationViewModel
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.profilescreens.loadContacts
//import com.example.groovyspotify.ui.profilescreens.loadImageFromUri
//import com.example.groovyspotify.ui.profilescreens.openGallery
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by  viewModels<AuthViewModel>()
    private val spotifyAuthViewModel by  viewModels<SpotifyApiViewModel>()
    private val navEliminationViewModel by viewModels<NavEliminationViewModel>()
    private val firestoreViewModel by viewModels<FirestoreViewModel>()
//    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
//    private lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroovySpotifyTheme {
                // A surface container using the 'background' color from the theme

                val requestPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (isGranted) {
                            // Permission granted, perform contact synchronization
                            loadContacts(context = this)
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
                    requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }

                NavigationScreen(viewModel = viewModel,spotifyApiViewModel = spotifyAuthViewModel,navEliminationViewModel = navEliminationViewModel,firestoreViewModel= firestoreViewModel)


            }

        }

    }



//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//
//    private fun hasReadStoragePermission(context: Context): Boolean {
//        return ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestReadStoragePermission() {
//        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//    }
//
//    private fun initializeActivityResults() {
//        requestPermissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted ->
//            if (isGranted) {
//                openGallery()
//            }
//        }
//
//        getContent = registerForActivityResult(
//            ActivityResultContracts.GetContent()
//        ) { uri: Uri? ->
//            uri?.let { selectedImageUri ->
//                loadImageFromUri(selectedImageUri)
//            }
//        }
//    }

//    private fun hasReadContactsPermission(context: Context): Boolean {
//        return ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.READ_CONTACTS
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun requestPermission(activity: ComponentActivity) {
//        activity.requestPermissions(
//            arrayOf(Manifest.permission.READ_CONTACTS),
//            READ_CONTACTS_PERMISSION_REQUEST
//
//        )
//    }

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