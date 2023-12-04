package com.gabrieleporcelli.imagetracker

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.gabrieleporcelli.imagetracker.application.theme.ImageTrackerTheme
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.ui.TrackerScreen
import org.junit.Rule
import org.junit.Test


class ComposeTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun grantLocationPermissionShown() {
        composeTestRule.setContent {
            ImageTrackerTheme {
                val state = remember { mutableStateOf(TrackerState.TrackerStopped) }
                val trackedImages = remember { mutableStateOf(listOf<TrackedImage>()) }
                TrackerScreen(state, trackedImages) { }
            }
        }
        composeTestRule.onNodeWithText("Grant location permission").assertIsDisplayed()
    }
}