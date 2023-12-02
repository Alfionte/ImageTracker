package com.gabrieleporcelli.imagetracker.feature.domain

@Suppress("unused")
sealed interface TrackerState {
    data object NoPermission : TrackerState
    data object PermissionPermanentlyDenied : TrackerState
    data object TrackerStopped : TrackerState
    data object TrackerStarted : TrackerState
}

sealed interface TrackerViewAction {
    data object Start : TrackerViewAction
    data object Stop : TrackerViewAction
    data object Clean : TrackerViewAction
}

