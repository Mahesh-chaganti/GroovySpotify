package com.example.groovyspotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.theme.GroovySpotifyTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.groovyspotify.navigation.NavigationScreen
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by  viewModels<AuthViewModel>()
    private val spotifyAuthViewModel by  viewModels<SpotifyApiViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroovySpotifyTheme {
                // A surface container using the 'background' color from the theme

                    NavigationScreen(viewModel = viewModel,spotifyAuthViewModel = spotifyAuthViewModel)


            }
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GroovySpotifyTheme {
        MainScaffold()
    }
}