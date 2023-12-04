package com.gabrieleporcelli.imagetracker.feature.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.DeleteAllTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.FetchTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.GetTrackedImageStreamUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UpdateTrackedImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val deleteAllTrackedImageUseCase: DeleteAllTrackedImageUseCase,
    private val getTrackedImageStreamUseCase: GetTrackedImageStreamUseCase,
    private val updateTrackedImageUseCase: UpdateTrackedImageUseCase,
    private val fetchTrackedImageUseCase: FetchTrackedImageUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            getTrackedImageStreamUseCase
                .getTrackedImageStream()
                .collect { trackedImages ->
                    val sortedImages = if (trackedImages.isNotEmpty()) {
                        trackedImages.sortedByDescending { it.creationTime }
                    } else {
                        trackedImages
                    }
                    _trackedImages.update { sortedImages }
                }
        }
    }

    private suspend fun retrieveImageUrl(trackedImage: TrackedImage) {
        if (trackedImage.url == null) {
            val image = fetchTrackedImageUseCase.fetchTrackedImage(trackedImage.location, trackedImage.creationTime)
            image.getOrNull()?.let { safeImage ->
                updateTrackedImageUseCase.updateTrackedImage(safeImage)
            }
        }
    }

    private val _state: MutableStateFlow<TrackerState> = MutableStateFlow(TrackerState.TrackerStopped)
    val state = _state.asStateFlow()

    private val _trackedImages: MutableStateFlow<List<TrackedImage>> = MutableStateFlow(emptyList())
    val trackedImages = _trackedImages.asStateFlow()

    fun onAction(action: TrackerViewAction) {
        when (action) {
            TrackerViewAction.OnStart -> _state.update { TrackerState.TrackerStarted }
            TrackerViewAction.OnStop -> _state.update { TrackerState.TrackerStopped }
            TrackerViewAction.OnPermissionSettingsClicked -> _state.update { TrackerState.TrackerStopped }
            is TrackerViewAction.OnRetry -> viewModelScope.launch { retrieveImageUrl(action.trackedImage) }
            TrackerViewAction.OnClean -> viewModelScope.launch { deleteAllTrackedImageUseCase.deleteAllTrackedImage() }
        }
    }
}