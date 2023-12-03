@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)

package com.gabrieleporcelli.imagetracker.feature.ui.compose

import android.content.Intent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.gabrieleporcelli.imagetracker.R
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction
import com.gabrieleporcelli.imagetracker.feature.domain.location.LocationService
import com.gabrieleporcelli.imagetracker.feature.domain.location.LocationService.Companion.ACTION_STOP
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted

@ExperimentalPermissionsApi
@Composable
internal fun TopBar(
    state: State<TrackerState>,
    permissionState: PermissionState,
    onAction: (action: TrackerViewAction) -> Unit
) {
    TopAppBar(
        title = { Text("Image Tracker App") },
        actions = {
            val applicationContext = LocalContext.current

            when {
                permissionState.status.isGranted && state.value == TrackerState.TrackerStarted -> {
                    Button(
                        onClick = {
                            Intent(applicationContext, LocationService::class.java).apply {
                                action = ACTION_STOP
                                applicationContext.startService(this)
                            }
                            onAction(TrackerViewAction.OnStop)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.stop_tracking_button),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                        )
                    }
                }

                permissionState.status.isGranted && state.value == TrackerState.TrackerStopped -> {
                    Button(
                        onClick = {
                            Intent(applicationContext, LocationService::class.java).apply {
                                action = LocationService.ACTION_START
                                applicationContext.startService(this)
                            }
                            onAction(TrackerViewAction.OnStart)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.start_tracking_button),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                        )
                    }
                }

                else -> Unit
            }
        }
    )
}