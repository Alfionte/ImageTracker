@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)

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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TrackerScreen(
    state: State<TrackerState>,
    trackedImages: State<List<TrackedImage>>,
    onAction: (action: TrackerViewAction) -> Unit
) {
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    Scaffold(
        topBar = { TopBar(state, locationPermissionState, onAction) },
        content = { padding -> Content(state, trackedImages, locationPermissionState, onAction, padding) },
    )
}

@Suppress("UNUSED_PARAMETER")
@Composable
private fun Content(
    state: State<TrackerState>,
    trackedImages: State<List<TrackedImage>>,
    locationPermissionState: PermissionState,
    onAction: (action: TrackerViewAction) -> Unit,
    padding: PaddingValues
) {
    Crossfade(targetState = locationPermissionState.status, label = "Permission CrossFade") { locationPermission ->
        when {
            locationPermission.isGranted -> TrackedImages(trackedImages, onAction)
            locationPermission.shouldShowRationale.not() -> PermissionDenied(locationPermissionState)
            else -> PermissionDeniedPermanently()
        }
    }
}