package com.example.groovyspotify.navigation



import androidx.compose.animation.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.groovyspotify.ui.home.HomeScreen
import com.example.groovyspotify.ui.auth.LoginAuthScreen
import com.example.groovyspotify.ui.auth.SignUpScreen
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.spotifyauth.ClientCredentialsAuthScreen
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationScreen(
    viewModel: AuthViewModel, spotifyAuthViewModel: SpotifyApiViewModel
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = "ClientCredentialsAuthScreen") {
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
        ) { HomeScreen(viewModel, spotifyAuthViewModel, navController) }
//        composable(
//            "SpotifyAuthenticationScreen",
//            enterTransition = {
//                when (initialState.destination.route) {
//                    "LoginAuthScreen"->
//                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
//                    else -> null
//                }
//            },
//            exitTransition = {
//                when (targetState.destination.route) {
//                    "LoginAuthScreen" ->
//                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
//                    else -> null
//                }
//            },
//            popEnterTransition = {
//                when (initialState.destination.route) {
//                    "LoginAuthScreen" ->
//                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
//                    else -> null
//                }
//            },
//            popExitTransition = {
//                when (targetState.destination.route) {
//                    "LoginAuthScreen"->
//                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
//                    else -> null
//                }
//            }
//        ) { SpotifyAuthenticationScreen(spotifyAuthViewModel, navController) }
        composable(
            "ClientCredentialsAuthScreen",
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
        ) { ClientCredentialsAuthScreen(spotifyAuthViewModel, navController) }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {

}

