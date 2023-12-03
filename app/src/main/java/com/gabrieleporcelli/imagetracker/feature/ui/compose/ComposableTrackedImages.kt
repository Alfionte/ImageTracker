package com.gabrieleporcelli.imagetracker.feature.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.gabrieleporcelli.imagetracker.R
import com.gabrieleporcelli.imagetracker.application.theme.Yellow
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction

@Composable
internal fun TrackedImages(
    trackedImages: State<List<TrackedImage>>,
    onAction: (action: TrackerViewAction) -> Unit
) {
    val images = trackedImages.value
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Absolute.spacedBy(8.dp),
        content = {
            items(images.size) { index ->
                val trackedImage = images[index]
                if (trackedImage.url != null) {
                    AsyncTrackedImage(trackedImage)
                } else {
                    RetryImage(onAction, trackedImage)
                }
            }
        })
}

@Composable
private fun AsyncTrackedImage(trackedImage: TrackedImage) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .background(Yellow)
            .height(250.dp),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Yellow),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        model = trackedImage.url,
        contentDescription = trackedImage.url,
    )
}

@Composable
private fun RetryImage(
    onAction: (action: TrackerViewAction) -> Unit,
    trackedImage: TrackedImage
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Yellow)
            .height(250.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { onAction(TrackerViewAction.OnRetry(trackedImage)) }) {
            Text(
                text = stringResource(R.string.image_retry_button),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
        }
    }
}