package com.gabrieleporcelli.imagetracker.feature.data

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageLocalDataSource
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackedImageRepositoryImpl @Inject constructor(
    private val localDataSource: TrackerImageLocalDataSource
) : TrackedImageRepository {

    override fun getTrackedImageStream(): Flow<List<TrackedImage>> =
        localDataSource.getTrackedImageStream()

    override suspend fun saveTrackedImage(trackedImage: TrackedImage) =
        localDataSource.saveTrackedImage(trackedImage)

    override suspend fun updateTrackedImage(trackedImage: TrackedImage) =
        localDataSource.updateTrackedImage(trackedImage)

    override suspend fun deleteAllTrackedImage() =
        localDataSource.deleteAllTrackedImage()

}