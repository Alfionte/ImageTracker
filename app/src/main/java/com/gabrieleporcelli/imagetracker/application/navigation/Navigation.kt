package com.gabrieleporcelli.imagetracker.application.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gabrieleporcelli.imagetracker.feature.navigation.trackerScreen

@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreen.TrackerScreen.route,
    ) {
        trackerScreen()
    }
}