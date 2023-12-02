package com.gabrieleporcelli.imagetracker.feature.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerState
import com.gabrieleporcelli.imagetracker.feature.domain.TrackerViewAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<TrackerState> = MutableStateFlow(TrackerState.NoPermission)
    val state = _state.asStateFlow()


    fun onAction(action: TrackerViewAction) {
        when (action) {
            TrackerViewAction.Start -> TODO()
            TrackerViewAction.Stop -> TODO()
        }
    }
}