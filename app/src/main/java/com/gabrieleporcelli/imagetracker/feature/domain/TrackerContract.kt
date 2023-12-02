package com.gabrieleporcelli.imagetracker.feature.domain

@Suppress("unused")
sealed interface TrackerState {
    data object NoPermission : TrackerState
    data object PermissionPermanentlyDenied : TrackerState
    data object TrackerStopped : TrackerState
    data object TrackerStarted : TrackerState
}

sealed interface TrackerViewAction {
    data object OnPermissionClicked : TrackerViewAction
    data object OnPermissionSettingsClicked : TrackerViewAction
    data object OnStart : TrackerViewAction
    data object OnStop : TrackerViewAction
    data object OnClean : TrackerViewAction
}

