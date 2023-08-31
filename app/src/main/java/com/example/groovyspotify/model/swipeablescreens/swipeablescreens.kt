package com.example.groovyspotify.model.swipeablescreens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
//import com.example.groovyspotify.ui.home.AccountInfoScreen

data class SwipeableScreens(val label: String, val icon: ImageVector, val content: @Composable () -> Unit)
