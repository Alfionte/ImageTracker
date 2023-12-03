package com.gabrieleporcelli.imagetracker.feature.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.DeleteAllTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.FetchTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.GetTrackedImageStreamUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.SaveTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UpdateTrackedImageUseCase
import com.google.android.gms.maps.model.LatLng
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
    private val saveTrackedImageUseCase: SaveTrackedImageUseCase,
    private val updateTrackedImageUseCase: UpdateTrackedImageUseCase,
    private val fetchTrackedImageUseCase: FetchTrackedImageUseCase,
) : ViewModel() {

    init {
        fetchImages()
        viewModelScope.launch {
            getTrackedImageStreamUseCase.getTrackedImageStream().collect { trackedImages ->
                _trackedImages.update { trackedImages }
            }
        }
    }

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
            is TrackerViewAction.OnRetry -> fetchImages()
            TrackerViewAction.OnClean -> viewModelScope.launch { deleteAllTrackedImageUseCase.deleteAllTrackedImage() }
        }
    }

    private fun fetchImages() {
        viewModelScope.launch {
            val image1 = fetchTrackedImageUseCase.fetchTrackedImage(LatLng(28.39223433708077, -16.61376387081628))
            val image2 = fetchTrackedImageUseCase.fetchTrackedImage(LatLng(28.39544263301374, -16.57011996777058))
            val image3 = fetchTrackedImageUseCase.fetchTrackedImage(LatLng(28.386260282771687, -16.541839658410733))
            val image4 = fetchTrackedImageUseCase.fetchTrackedImage(LatLng(28.39544263301375, -16.57011996777058))

            image1.getOrNull()?.let { safeImage1 -> saveTrackedImageUseCase.saveTrackedImage(safeImage1) }
            image2.getOrNull()?.let { safeImage2 -> saveTrackedImageUseCase.saveTrackedImage(safeImage2) }
            image3.getOrNull()?.let { safeImage3 -> saveTrackedImageUseCase.saveTrackedImage(safeImage3) }
            image4.getOrNull()?.let { safeImage4 -> saveTrackedImageUseCase.saveTrackedImage(safeImage4) }
        }
    }
}