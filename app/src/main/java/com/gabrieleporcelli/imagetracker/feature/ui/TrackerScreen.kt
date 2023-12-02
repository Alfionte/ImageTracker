package com.gabrieleporcelli.imagetracker.feature.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabrieleporcelli.imagetracker.R
import com.gabrieleporcelli.imagetracker.application.theme.BrownDark
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction

@Composable
fun TrackerScreen(
    state: TrackerState,
    trackedImages: List<TrackedImage>,
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
    state: TrackerState,
    trackedImages: List<TrackedImage>,
    onAction: (action: TrackerViewAction) -> Unit,
    padding: PaddingValues
) {
    when (state) {
        TrackerState.NoPermission -> PermissionDenied(onAction)
        TrackerState.PermissionPermanentlyDenied -> PermissionDeniedPermanently(onAction)
        TrackerState.TrackerStarted, TrackerState.TrackerStopped -> TrackedImages(
            trackedImages,
            onAction
        )
    }
}

@Composable
private fun TopBar(state: TrackerState, onAction: (action: TrackerViewAction) -> Unit) {
    TopAppBar(
        title = { Text("Image Tracker App") },
        actions = {
            when (state) {
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

@Composable
fun TrackedImages(
    trackedImages: List<TrackedImage>,
    onAction: (action: TrackerViewAction) -> Unit
) {
    LazyColumn(content = {
        items(trackedImages.size) { index ->
            val trackedImage = trackedImages[index]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
            )
            if (trackedImage.isValid) {
                Text(text = trackedImage.url!!)
            } else {
                Text(
                    modifier = Modifier.clickable { onAction(TrackerViewAction.OnRetry(trackedImage)) },
                    text = "Retry"
                )
            }
        }
    })
}


@Composable
fun PermissionDenied(onAction: (action: TrackerViewAction) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.trk_location_icon),
                contentDescription = stringResource(R.string.missing_location),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.missing_location),
                textAlign = TextAlign.Center,
                color = BrownDark,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(TrackerViewAction.OnPermissionClicked) }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.missing_location_button),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@Composable
fun PermissionDeniedPermanently(onAction: (action: TrackerViewAction) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.trk_location_icon),
                contentDescription = stringResource(R.string.missing_location_permanent),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.missing_location_permanent),
                textAlign = TextAlign.Center,
                color = BrownDark,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(TrackerViewAction.OnPermissionSettingsClicked) }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.missing_location_permanent_button),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }
    }
}

