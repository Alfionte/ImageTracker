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

    /*
    val image1 = fetchTrackedImageUseCase.fetchTrackedImage(LatLng(28.39223433708077, -16.61376387081628))
    val image2 = fetchTrackedImageUseCase.fetchTrackedImage(LatLng(28.39544263301374, -16.57011996777058))
    val image3 = fetchTrackedImageUseCase.fetchTrackedImage(LatLng(28.386260282771687, -16.541839658410733))
    val image4 = fetchTrackedImageUseCase.fetchTrackedImage(LatLng(28.39544263301375, -16.57011996777058))
    */

    init {
        viewModelScope.launch {
            getTrackedImageStreamUseCase.getTrackedImageStream().collect { trackedImages ->
                if (trackedImages.isNotEmpty()) {
                    val lastImage = trackedImages.last()
                    retrieveImageUrl(lastImage)
                    _trackedImages.update { trackedImages }
                }
            }
        }
    }

    private suspend fun retrieveImageUrl(trackedImage: TrackedImage) {
        if (trackedImage.url == null) {
            val image = fetchTrackedImageUseCase.fetchTrackedImage(trackedImage.location)
            image.getOrNull()?.let { safeImage -> updateTrackedImageUseCase.updateTrackedImage(safeImage) }
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