package com.gabrieleporcelli.imagetracker.feature.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<TrackerState> = MutableStateFlow(TrackerState.NoPermission)
    val state = _state.asStateFlow()

    private val _trackedImages: MutableStateFlow<List<TrackedImage>> = MutableStateFlow(emptyList())
    val trackedImages = _trackedImages.asStateFlow()

    fun onAction(action: TrackerViewAction) {
        when (action) {
            TrackerViewAction.OnStart -> _state.update { TrackerState.TrackerStarted }
            TrackerViewAction.OnStop -> _state.update { TrackerState.TrackerStopped }
            TrackerViewAction.OnPermissionClicked -> _state.update { TrackerState.PermissionPermanentlyDenied }
            TrackerViewAction.OnPermissionSettingsClicked -> _state.update { TrackerState.TrackerStopped }
            is TrackerViewAction.OnRetry -> TODO()
            TrackerViewAction.OnClean -> TODO()
        }
    }
}