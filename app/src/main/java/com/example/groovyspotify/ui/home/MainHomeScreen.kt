package com.example.groovyspotify.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.R
import com.example.groovyspotify.model.swipeablescreens.SwipeableScreens
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainHomeScreen(firestoreViewModel: FirestoreViewModel?,listOfSwipeableScreens: List<SwipeableScreens>?, navController: NavController) {
    // This scope is necessary to change the tab using animation
    val scope = rememberCoroutineScope()
    // I'm using a list of images here

    // This page state will be used by BottomAppbar and HorizontalPager
    val pageState = rememberPagerState( listOfSwipeableScreens!!.size /2)
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.Black,
                content = {
                    listOfSwipeableScreens?.forEachIndexed { i, page ->
                        BottomNavigationItem(
                            icon = {
                                Icon(imageVector = page.icon, "Page $i") },
                            // here's the trick. the selected tab is based
                            // on HorizontalPager state.
                            selected = i == pageState.currentPage,
                            onClick = {
                                // When a tab is selected,
                                // the page is updated
                                scope.launch {
                                    pageState.animateScrollToPage(i)
                                }
                            },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.Gray,
                            label = { Text(text = page.label) }
                        )
                    }
                }
            )
        },
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = pageState,
           pageCount = listOfSwipeableScreens!!.size
        ) { page ->
           listOfSwipeableScreens[page].content()
            val scope = rememberCoroutineScope()
            LaunchedEffect(key1 = null) {
                scope.launch {

                    firestoreViewModel?.getUserProfile()
                }
            }
        }
    }
}



@Preview
@Composable
fun PreviewBottomNavigationWithSwipeableScreens() {
   MainHomeScreen(null,null,rememberNavController())
}
