package com.gabrieleporcelli.imagetracker.feature.domain.usecases

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetTrackedImageStreamUseCase {
    fun getTrackedImageStream(): Flow<List<TrackedImage>>
}

class GetTrackedImageStreamUseCaseImpl @Inject constructor(
    private val repository: TrackedImageRepository
) : GetTrackedImageStreamUseCase {
    override fun getTrackedImageStream(): Flow<List<TrackedImage>> = repository.getTrackedImageStream()
}