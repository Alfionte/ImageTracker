package com.gabrieleporcelli.imagetracker.feature.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction
import com.gabrieleporcelli.imagetracker.feature.ui.compose.PermissionDenied
import com.gabrieleporcelli.imagetracker.feature.ui.compose.PermissionDeniedPermanently
import com.gabrieleporcelli.imagetracker.feature.ui.compose.TopBar
import com.gabrieleporcelli.imagetracker.feature.ui.compose.TrackedImages

@Composable
fun TrackerScreen(
    state: State<TrackerState>,
    trackedImages: State<List<TrackedImage>>,
    onAction: (action: TrackerViewAction) -> Unit
) {
    Scaffold(
        topBar = { TopBar(state, onAction) },
        content = { padding -> Content(state, trackedImages, onAction, padding) },
    )
}

@Suppress("UNUSED_PARAMETER")
@Composable
private fun Content(
    state: State<TrackerState>,
    trackedImages: State<List<TrackedImage>>,
    onAction: (action: TrackerViewAction) -> Unit,
    padding: PaddingValues
) {
    Crossfade(targetState = state.value, label = "Permission CrossFade") { screenState ->
        when (screenState) {
            TrackerState.NoPermission -> PermissionDenied(onAction)
            TrackerState.PermissionPermanentlyDenied -> PermissionDeniedPermanently(onAction)
            TrackerState.TrackerStarted, TrackerState.TrackerStopped -> TrackedImages(trackedImages, onAction)
        }
    }
}