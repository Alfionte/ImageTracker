package com.gabrieleporcelli.imagetracker.feature.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction

@Composable
fun TrackerScreen(state: TrackerState, onAction: (action: TrackerViewAction) -> Unit) {
    Scaffold(
        topBar = { TopBar() },
        content = { padding -> content(padding) },
    )
}

@Composable
private fun content(padding: PaddingValues) {
    Box(modifier = Modifier.padding(16.dp)) {
        Text("BodyContent")
    }
}

@Composable
private fun TopBar() {
    TopAppBar(title = { Text("Image Tracker App") })
}