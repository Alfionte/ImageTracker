package com.gabrieleporcelli.imagetracker.feature.domain.repository

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import kotlinx.coroutines.flow.Flow

interface TrackedImageRepository {

    fun getTrackedImageStream(): Flow<List<TrackedImage>>

    suspend fun saveTrackedImage(trackedImage: TrackedImage)

    suspend fun updateTrackedImage(trackedImage: TrackedImage)

    suspend fun deleteAllTrackedImage()
}