package com.gabrieleporcelli.imagetracker.feature.domain.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gabrieleporcelli.imagetracker.application.navigation.NavScreen
import com.gabrieleporcelli.imagetracker.feature.ui.TrackerScreen
import com.gabrieleporcelli.imagetracker.feature.ui.viewmodel.TrackerViewModel

fun NavGraphBuilder.trackerScreen() {
    composable(NavScreen.TrackerScreen.route) {
        val viewModel = hiltViewModel<TrackerViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()
        val trackedImages = viewModel.trackedImages.collectAsStateWithLifecycle()
        TrackerScreen(state, trackedImages) { action ->
            viewModel.onAction(action)
        }
    }
}