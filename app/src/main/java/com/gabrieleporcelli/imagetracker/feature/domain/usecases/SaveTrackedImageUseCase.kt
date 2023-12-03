package com.gabrieleporcelli.imagetracker.feature.domain.usecases

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import javax.inject.Inject

interface SaveTrackedImageUseCase {
    suspend fun saveTrackedImage(trackedImage: TrackedImage)
}

class SaveTrackedImageUseCaseImpl @Inject constructor(
    private val repository: TrackedImageRepository
) : SaveTrackedImageUseCase {
    override suspend fun saveTrackedImage(trackedImage: TrackedImage) =
        repository.saveTrackedImage(trackedImage)
}