package com.gabrieleporcelli.imagetracker.application.navigation


sealed interface NavScreen {
    val route: String

    object TrackerScreen : NavScreen {
        override val route = "kmt_tracker_screen"
    }
}