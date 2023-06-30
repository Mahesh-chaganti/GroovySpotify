package com.example.groovyspotify.navigation


import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.spotifyapidata.playlist.Playlist
import com.example.groovyspotify.model.spotifyapidata.track.TrackResponse
import com.example.groovyspotify.model.swipeablescreens.SwipeableScreens
import com.example.groovyspotify.ui.auth.LoginAuthScreen
import com.example.groovyspotify.ui.auth.SignUpScreen
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.exoplayer.ExoplayerImpl
import com.example.groovyspotify.ui.exoplayer.NavEliminationViewModel
import com.example.groovyspotify.ui.fcm.FCMViewModel
import com.example.groovyspotify.ui.home.AccountInfoScreen
import com.example.groovyspotify.ui.home.FriendsScreen
import com.example.groovyspotify.ui.home.HomeScreen
import com.example.groovyspotify.ui.home.MainHomeScreen
import com.example.groovyspotify.ui.home.MusicRoomsScreen
import com.example.groovyspotify.ui.home.PlaylistSongs
import com.example.groovyspotify.ui.home.ProfileCard
import com.example.groovyspotify.ui.home.SearchScreen
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.profilescreens.PhotoUploadScreen
import com.example.groovyspotify.ui.profilescreens.ProfileFeaturedAudio
import com.example.groovyspotify.ui.profilescreens.ProfileScreenArtist
import com.example.groovyspotify.ui.profilescreens.ProfileScreenLanguage
import com.example.groovyspotify.ui.realtimedatabase.RealtimeDatabaseViewModel
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationScreen(
    viewModel: AuthViewModel,
    spotifyApiViewModel: SpotifyApiViewModel,
    navEliminationViewModel: NavEliminationViewModel,
    firestoreViewModel: FirestoreViewModel,
    fcmViewModel: FCMViewModel,
    realtimeDatabaseViewModel: RealtimeDatabaseViewModel
) {
    val navController = rememberAnimatedNavController()


    val listOfSwipeableScreens = listOf(
        SwipeableScreens(
            "Account", Icons.Filled.AccountCircle, content = {
                AccountInfoScreen(
                    viewModel = viewModel,
                    firestoreViewModel = firestoreViewModel,
                    navEliminationViewModel = navEliminationViewModel,
                    navController = navController
                )
            }
        ),
        SwipeableScreens(
            "Search", Icons.Filled.Search, content = {
                SearchScreen(
                    navController = navController,
                    spotifyApiViewModel = spotifyApiViewModel
                )
            }
        ),
        SwipeableScreens(
            "Discover", Icons.Filled.List, content = {
                HomeScreen(
                    navController = navController,
                    firestoreViewModel = firestoreViewModel,
                    fcmViewModel = fcmViewModel
                )
            }
        ),
        SwipeableScreens(
            "Friends", Icons.Filled.Face, content = {
                FriendsScreen(
                    viewModel,firestoreViewModel,fcmViewModel,realtimeDatabaseViewModel
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







    AnimatedNavHost(
        navController,
        startDestination = if (viewModel.currentUser != null) "MainHomeScreen" else "LoginScreen"
    ) {
        composable(
            "MainHomeScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "ProfileCard" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "ProfileCard" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "ProfileCard" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "ProfileCard" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            MainHomeScreen(
                viewModel = viewModel,
                firestoreViewModel = firestoreViewModel,
                spotifyApiViewModel = spotifyApiViewModel,
                listOfSwipeableScreens = listOfSwipeableScreens,
                navController = navController
            )
        }
        composable(
            "LoginAuthScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "OTPScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "OTPScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "OTPScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "OTPScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) { LoginAuthScreen(viewModel, firestoreViewModel, navController) }
        composable(
            "SignUpScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) { SignUpScreen(viewModel, firestoreViewModel = firestoreViewModel, navController) }

        composable(
            "ProfileScreenLanguage",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            ProfileScreenLanguage(
                spotifyApiViewModel = spotifyApiViewModel,
                firestoreViewModel = firestoreViewModel,
                navController
            )
        }
        composable(
            "ProfileScreenArtist",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            ProfileScreenArtist(
                spotifyApiViewModel = spotifyApiViewModel,
                firestoreViewModel = firestoreViewModel,
                navController
            )
        }
        composable(
            "ProfileFeaturedAudio",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            ProfileFeaturedAudio(
                spotifyApiViewModel = spotifyApiViewModel,
                navEliminationViewModel = navEliminationViewModel,
                navController = navController
            )
        }
        composable(
            "ProfileCard/{isHomeScreen}/{userProfile}",
            arguments = listOf(navArgument("isHomeScreen") { type = NavType.BoolType },
                navArgument("userProfile"){type = UserProfileType() }
            ),

            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {

            ProfileCard(
                userProfile = it.arguments?.getParcelable("userProfile"),
                navController = navController,
                isHomeScreen = it.arguments!!.getBoolean("isHomeScreen")
            )

        }
        composable(
            "ExoPlayerImpl/{track}",
            arguments = listOf(navArgument("track") {type = TrackType() }
            ),
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {

            it.arguments?.getParcelable<TrackResponse>("track")?.let { it1 ->
                ExoplayerImpl(
                    navEliminationViewModel = navEliminationViewModel,
                    firestoreViewModel = firestoreViewModel,
                    navController =  navController,
                    track = it1
                )
            }

        }
        composable(
            "PlaylistSongs/{playlist}",
            arguments = listOf(navArgument("playlist") {type = PlaylistType() }
            ),
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {


            it.arguments?.getParcelable<Playlist>("playlist")?.let { it1 ->
                PlaylistSongs(

                    navController =  navController,
                    playlist = it1
                )
            }


        }

        composable(
            "AccountInfoScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) {
            AccountInfoScreen(
                viewModel = viewModel,
                firestoreViewModel = firestoreViewModel,
                navEliminationViewModel = navEliminationViewModel,
                navController
            )
        }
        composable(
            "PhotoUploadScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "LoginAuthScreen" ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "LoginAuthScreen" ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }
        ) { PhotoUploadScreen(firestoreViewModel = firestoreViewModel, navController) }

    }
}


@Composable
fun WelcomeScreen(navController: NavHostController) {

}

