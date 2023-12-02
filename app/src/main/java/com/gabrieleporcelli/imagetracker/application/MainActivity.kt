package com.gabrieleporcelli.imagetracker.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gabrieleporcelli.imagetracker.application.navigation.Navigation
import com.gabrieleporcelli.imagetracker.application.theme.ImageTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageTrackerTheme {
                Navigation()
            }
        }
    }
}
