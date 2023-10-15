package com.example.groovyspotify.navigation


import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.groovyspotify.model.swipeablescreens.SwipeableScreens
import com.example.groovyspotify.ui.ParentViewModel
import com.example.groovyspotify.ui.auth.LoginAuthScreen
import com.example.groovyspotify.ui.auth.SignUpScreen
import com.example.groovyspotify.ui.auth.LoginViewModel
import com.example.groovyspotify.ui.auth.SignUpViewModel
import com.example.groovyspotify.ui.fcm.FCMViewModel
import com.example.groovyspotify.ui.genderanddob.GenderAndDobScreen
import com.example.groovyspotify.ui.genderanddob.GenderAndDobViewModel
import com.example.groovyspotify.ui.home.FeedScreen
import com.example.groovyspotify.ui.home.accountinfo.AccountInfoScreen
import com.example.groovyspotify.ui.profilescreens.audio.AlbumSongs
//import com.example.groovyspotify.ui.home.AccountInfoScreen
import com.example.groovyspotify.ui.home.MainHomeScreen
import com.example.groovyspotify.ui.home.musicrooms.MusicRoomsScreen
import com.example.groovyspotify.ui.profilescreens.audio.PlaylistSongs
import com.example.groovyspotify.ui.home.SearchScreen
import com.example.groovyspotify.ui.home.accountinfo.AccountInfoViewModel
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.profilescreens.PhotoUploadScreen
import com.example.groovyspotify.ui.profilescreens.audio.FeaturedAudioViewModel
import com.example.groovyspotify.ui.profilescreens.audio.ProfileFeaturedAudio
import com.example.groovyspotify.ui.profilescreens.languagesandartists.LanguagesAndArtistsViewModel
import com.example.groovyspotify.ui.profilescreens.languagesandartists.ProfileScreenLanguagesAndArtists
import com.example.groovyspotify.ui.realtimedatabase.RealtimeDatabaseViewModel
import com.example.groovyspotify.ui.spotifyauth.SpotifyApiViewModel
import com.example.groovyspotify.ui.swipe.SwipeScreen
import com.example.groovyspotify.ui.swipe.SwipeScreenViewModel
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationScreen(
    viewModel: LoginViewModel,
    spotifyApiViewModel: SpotifyApiViewModel,
//    navEliminationViewModel: NavEliminationViewModel,
    firestoreViewModel: FirestoreViewModel,
    fcmViewModel: FCMViewModel,
    realtimeDatabaseViewModel: RealtimeDatabaseViewModel,
    signUpViewModel: SignUpViewModel,
    featuredAudioViewModel: FeaturedAudioViewModel,
    languagesAndArtistsViewModel: LanguagesAndArtistsViewModel,
    genderAndDobViewModel: GenderAndDobViewModel,
    parentViewModel: ParentViewModel,
    swipeScreenViewModel: SwipeScreenViewModel,
    accountInfoViewModel: AccountInfoViewModel
) {

    val navController = rememberAnimatedNavController()
    val x = parentViewModel.popupNotification.value
    Log.d("NavScreen", "NavigationScreen: ${x?.getContentOrNull().toString()}")
//    NotificationMessage(vm = parentViewModel)
fun popUp() {
    navController.popBackStack()
}

    fun navigateTo(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true
           }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }


    val listOfSwipeableScreens = listOf(
        SwipeableScreens(
            "Account", Icons.Filled.AccountCircle, content = {
                AccountInfoScreen(loginViewModel = viewModel, openGenderAndDob = {route-> navigateTo(route)},
                    openAndPopUpLoginScreen = {route,popup-> navigateAndPopUp(route,popup)},
                    openLanguagesAndArtists = {route -> navigateTo(route)},
                    openSearch = {route -> navigateTo(route)},
                    accountInfoViewModel = accountInfoViewModel,)
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
                SwipeScreen(
                    swipeScreenViewModel = swipeScreenViewModel,
                    loginViewModel = viewModel
                )
            }
        ),
        SwipeableScreens(
            "Feed", Icons.Filled.Face, content = {
                FeedScreen(
                    accountInfoViewModel,
                    openSearch = {route -> navigateTo(route)}
                )
            }
        ),
        SwipeableScreens(
            "Rooms", Icons.Filled.Edit, content = {
                MusicRoomsScreen(
                    loginViewModel = viewModel
                )
            }
        ),

        )





    AnimatedNavHost(
        navController,
        startDestination =
//        if (AccountServiceImpl.currentUser != null)
        "LoginAuthScreen"
//        else "LoginAuthScreen"
    ) {
        composable(
            "MainHomeScreen",

            ) {
            MainHomeScreen(

                listOfSwipeableScreens = listOfSwipeableScreens,
            )
        }
        composable(
            "LoginAuthScreen",

            ) {
            LoginAuthScreen(loginViewModel = viewModel,
                openAndPopUp = { route, popUp -> navigateAndPopUp(route, popUp)},
                openScreen = {route -> clearAndNavigate(route)}

            )
        }

        composable(
            "SignUpScreen",

            ) {
            SignUpScreen(
                signUpViewModel,
                openAndPopUp = { route, popUp -> navigateAndPopUp(route, popUp) })
        }

        composable(
            "ProfileScreenLanguagesAndArtists",
            ) {
            ProfileScreenLanguagesAndArtists(
                languagesAndArtistsViewModel = languagesAndArtistsViewModel,
                openAndPopUp = {route, popUp -> navigateAndPopUp(route, popUp) }
            )
        }

        composable(
            "ProfileFeaturedAudio",
            ) {
            ProfileFeaturedAudio(

                featuredAudioViewModel,
                openScreen = { route -> navigateTo(route) },
                openAndPopUp = {route,popup -> navigateAndPopUp(route,popup)}
                

            )

        }
        composable("PlaylistSongs"){
            PlaylistSongs(featuredAudioViewModel = featuredAudioViewModel,
                openAndPopUp = {route,popup -> navigateAndPopUp(route,popup)}
            )
        }
        composable("AlbumSongs"){
            AlbumSongs(featuredAudioViewModel = featuredAudioViewModel,
                openAndPopUp = {route,popup -> navigateAndPopUp(route,popup)}
            )
        }
        composable("GenderAndDobScreen") {
            GenderAndDobScreen(genderAndDobViewModel = genderAndDobViewModel,
                openAndPopUp = { route, popup -> navigateAndPopUp(route, popup) }
            )
        }



//        composable(
//            "ProfileCard/{isHomeScreen}/{userProfile}",
//            arguments = listOf(navArgument("isHomeScreen") { type = NavType.BoolType },
//                navArgument("userProfile") { type = UserProfileType() }
//            ),
//
//
//            ) {
//
//            ProfileCard(
//                viewModel = viewModel,
//                userProfile = it.arguments?.getParcelable("userProfile"),
//                navController = navController,
//                isHomeScreen = it.arguments!!.getBoolean("isHomeScreen")
//            )
//
//        }
//        composable(
//            "ExoPlayerImpl/{track}",
//            arguments = listOf(navArgument("track") { type = TrackType() }
//            ),
//
//            ) {
//
//            it.arguments?.getParcelable<TrackResponse>("track")?.let { it1 ->
//                ExoplayerImpl(
//                    featuredAudioViewModel,
//                    navController = navController,
////                    track = it1
//                )
//            }
//
//        }

//        composable(
//            "PlaylistSongs/{playlist}",
//            arguments = listOf(navArgument("playlist") { type = PlaylistType() }
//            ),
//
//            ) {
//
//
//            it.arguments?.getParcelable<Playlist>("playlist")?.let { it1 ->
//                PlaylistSongs(
//
////                    navController = navController,
////                    playlist = it1
//                )
//            }
//
//
//        }

        composable(
            "AccountInfoScreen",

            ) {
//            AccountInfoScreen(
//                viewModel = viewModel,
//                firestoreViewModel = firestoreViewModel,
//                navEliminationViewModel = navEliminationViewModel,
//                navController
//            )
        }
        composable(
            "PhotoUploadScreen",

            ) { PhotoUploadScreen(firestoreViewModel = firestoreViewModel, navController) }

    }

}


@Composable
fun WelcomeScreen(navController: NavHostController) {

}

