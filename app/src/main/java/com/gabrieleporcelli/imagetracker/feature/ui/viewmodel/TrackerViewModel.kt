package com.gabrieleporcelli.imagetracker.feature.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.DeleteAllTrackedImageUseCase
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
            is TrackerViewAction.OnRetry -> {
                val retryImage = action.trackedImage
                viewModelScope.launch {
                    updateTrackedImageUseCase.updateTrackedImage(retryImage.copy(url = null))
                    updateTrackedImageUseCase.updateTrackedImage(retryImage)
                }
            }

            TrackerViewAction.OnClean -> viewModelScope.launch { deleteAllTrackedImageUseCase.deleteAllTrackedImage() }
        }
    }

    private fun fetchImages() {
        viewModelScope.launch {
            val image1 = TrackedImage(
                LatLng(28.39223433708077, -16.61376387081628),
                "https://images.unsplash.com/photo-1682695794947-17061dc284dd?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
            val image2 = TrackedImage(
                LatLng(28.39544263301374, -16.57011996777058),
                "https://images.unsplash.com/photo-1476611338391-6f395a0ebc7b?q=80&w=2342&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
            val image3 = TrackedImage(
                LatLng(28.386260282771687, -16.541839658410733), null
            )
            val image4 = TrackedImage(
                LatLng(28.39544263301375, -16.57011996777058),
                "https://images.unsplash.com/photo-1476611338391-6f395a0ebc7b?q=80&w=2342&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
            saveTrackedImageUseCase.saveTrackedImage(image1)
            saveTrackedImageUseCase.saveTrackedImage(image2)
            saveTrackedImageUseCase.saveTrackedImage(image3)
            saveTrackedImageUseCase.saveTrackedImage(image4)
        }
    }
}