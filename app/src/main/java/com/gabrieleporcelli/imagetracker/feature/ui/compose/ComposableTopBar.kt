package com.gabrieleporcelli.imagetracker.feature.ui.compose

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.gabrieleporcelli.imagetracker.R
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction

@Composable
internal fun TopBar(state: State<TrackerState>, onAction: (action: TrackerViewAction) -> Unit) {
    TopAppBar(
        title = { Text("Image Tracker App") },
        actions = {
            when (state.value) {
                TrackerState.TrackerStarted -> {
                    Button(
                        onClick = { onAction(TrackerViewAction.OnStop) }
                    ) {
                        Text(
                            text = stringResource(R.string.stop_tracking_button),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                        )
                    }
                }

                TrackerState.TrackerStopped -> {
                    Button(
                        onClick = { onAction(TrackerViewAction.OnStart) }
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