package com.gabrieleporcelli.imagetracker.feature.domain.usecases

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import javax.inject.Inject

interface UpdateTrackedImageUseCase {
    suspend fun updateTrackedImage(trackedImage: TrackedImage)
}

class UpdateTrackedImageUseCaseImpl @Inject constructor(
    private val repository: TrackedImageRepository
) : UpdateTrackedImageUseCase {
    override suspend fun updateTrackedImage(trackedImage: TrackedImage) =
        repository.updateTrackedImage(trackedImage)
}