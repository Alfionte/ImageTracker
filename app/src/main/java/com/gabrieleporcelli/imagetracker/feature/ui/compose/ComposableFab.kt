@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)

package com.gabrieleporcelli.imagetracker.feature.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import com.gabrieleporcelli.imagetracker.R
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted

@ExperimentalPermissionsApi
@Composable
internal fun Fab(
    permissionState: PermissionState,
    trackedImages: State<List<TrackedImage>>,
    onAction: (action: TrackerViewAction) -> Unit
) {
    AnimatedVisibility(visible = permissionState.status.isGranted && trackedImages.value.isNotEmpty()) {
        FloatingActionButton(onClick = { onAction(TrackerViewAction.OnClean) }) {
            Text(text = stringResource(R.string.fab_clean))
        }
    }
}