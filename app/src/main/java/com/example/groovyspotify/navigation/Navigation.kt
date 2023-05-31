package com.example.groovyspotify.navigation



import androidx.compose.animation.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
//import com.example.groovyspotify.ui.home.HomeScreen
import com.example.groovyspotify.ui.auth.LoginAuthScreen
import com.example.groovyspotify.ui.auth.SignUpScreen
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.exoplayer.ExoplayerImpl
import com.example.groovyspotify.ui.exoplayer.NavEliminationViewModel
import com.example.groovyspotify.ui.home.HomeScreen
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
    viewModel: AuthViewModel, spotifyAuthViewModel: SpotifyApiViewModel,navEliminationViewModel: NavEliminationViewModel
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = "LoginAuthScreen") {
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
        ) { LoginAuthScreen(viewModel, navController) }
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
        ) { SignUpScreen(viewModel, navController) }
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
        ) { ProfileScreenLanguage( spotifyApiViewModel = spotifyAuthViewModel,navController) }
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
        ) { ProfileScreenArtist(spotifyApiViewModel = spotifyAuthViewModel, navController) }
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
        ) { ExoplayerImpl(navEliminationViewModel = navEliminationViewModel, navController) }
        composable(
            "HomeScreen",
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
        ) { HomeScreen(viewModel = viewModel, navController) }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {

}

