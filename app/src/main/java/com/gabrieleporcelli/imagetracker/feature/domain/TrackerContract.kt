package com.gabrieleporcelli.imagetracker.feature.domain

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage

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

    data class OnRetry(val trackedImage: TrackedImage) : TrackerViewAction
    data object OnClean : TrackerViewAction
}

