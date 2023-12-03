package com.gabrieleporcelli.imagetracker.feature.domain.usecases

import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import javax.inject.Inject

interface DeleteAllTrackedImageUseCase {
    suspend fun deleteAllTrackedImage()
}

class DeleteAllTrackedImageUseCaseImpl @Inject constructor(
    private val repository: TrackedImageRepository
) : DeleteAllTrackedImageUseCase {
    override suspend fun deleteAllTrackedImage() =
        repository.deleteAllTrackedImage()
}