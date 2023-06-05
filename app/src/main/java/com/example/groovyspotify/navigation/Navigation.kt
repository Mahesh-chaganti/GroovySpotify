package com.example.groovyspotify.navigation



import androidx.compose.animation.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.groovyspotify.model.swipeablescreens.SwipeableScreens
//import com.example.groovyspotify.ui.home.HomeScreen
import com.example.groovyspotify.ui.auth.LoginAuthScreen
import com.example.groovyspotify.ui.auth.SignUpScreen
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.exoplayer.ExoplayerImpl
import com.example.groovyspotify.ui.exoplayer.NavEliminationViewModel
import com.example.groovyspotify.ui.home.AccountInfoScreen
import com.example.groovyspotify.ui.home.FriendsScreen
import com.example.groovyspotify.ui.home.HomeScreen
import com.example.groovyspotify.ui.home.MainHomeScreen
import com.example.groovyspotify.ui.home.MusicRoomsScreen
import com.example.groovyspotify.ui.home.SearchScreen
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.profilescreens.PhotoUploadScreen
import com.example.groovyspotify.ui.profilescreens.ProfileFeaturedAudio
import com.example.groovyspotify.ui.profilescreens.ProfileScreenArtist
import com.example.groovyspotify.ui.profilescreens.ProfileScreenLanguage
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationScreen(
    viewModel: AuthViewModel, spotifyAuthViewModel: SpotifyApiViewModel,navEliminationViewModel: NavEliminationViewModel, firestoreViewModel: FirestoreViewModel
) {
    val navController = rememberAnimatedNavController()


    val listOfSwipeableScreens = listOf(
        SwipeableScreens(
            "Account", Icons.Filled.AccountCircle, content = {
                AccountInfoScreen(
                    viewModel = viewModel,
                    firestoreViewModel = firestoreViewModel,
                    navController = navController
                )
            }
        ),
        SwipeableScreens(
            "Search", Icons.Filled.Search, content = {
                SearchScreen(

                )
            }
        ),
        SwipeableScreens(
            "Discover", Icons.Filled.List, content = {
                HomeScreen(

                )
            }
        ),
        SwipeableScreens(
            "Friends", Icons.Filled.Face, content = {
               FriendsScreen(

                )
            }
        ),
        SwipeableScreens(
            "Rooms", Icons.Filled.Edit, content = {
              MusicRoomsScreen(

                )
            }
        ),

    )







    AnimatedNavHost(navController, startDestination = "MainHomeScreen") {
        composable(
            "MainHomeScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "OTPScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "OTPScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "OTPScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "OTPScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { MainHomeScreen(listOfSwipeableScreens = listOfSwipeableScreens,navController) }
        composable(
            "LoginAuthScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "OTPScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "OTPScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "OTPScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "OTPScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { LoginAuthScreen(viewModel, firestoreViewModel,navController) }
        composable(
            "SignUpScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen"->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen"->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { SignUpScreen(viewModel, firestoreViewModel = firestoreViewModel,navController) }
//
        composable(
            "ProfileScreenLanguage",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen"->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen"->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { ProfileScreenLanguage( spotifyApiViewModel = spotifyAuthViewModel,firestoreViewModel = firestoreViewModel,navController) }
        composable(
            "ProfileScreenArtist",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen"->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen"->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { ProfileScreenArtist(spotifyApiViewModel = spotifyAuthViewModel, firestoreViewModel = firestoreViewModel,navController) }
        composable(
            "ProfileFeaturedAudio",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen"->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen"->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { ProfileFeaturedAudio(spotifyApiViewModel = spotifyAuthViewModel, navEliminationViewModel = navEliminationViewModel, navController = navController) }
        composable(
            "ExoPlayerImpl",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen"->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen"->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { ExoplayerImpl(navEliminationViewModel = navEliminationViewModel, firestoreViewModel = firestoreViewModel,navController) }
        composable(
            "AccountInfoScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen"->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen"->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { AccountInfoScreen(viewModel = viewModel, firestoreViewModel = firestoreViewModel, navController) }
        composable(
            "PhotoUploadScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen"->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen"->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { PhotoUploadScreen(firestoreViewModel = firestoreViewModel,navController) }

    }
}



@Composable
fun WelcomeScreen(navController: NavHostController) {

}

