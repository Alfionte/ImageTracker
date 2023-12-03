package com.gabrieleporcelli.imagetracker.feature.data.localdatasource

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.model.toDomain
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.model.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackerImageLocalDataSource @Inject constructor(
    private val dao: TrackerImageDao,
    private val ioDispatcher: CoroutineDispatcher,
) {

    fun getTrackedImageStream(): Flow<List<TrackedImage>> =
        dao.getAll().map { imageEntityList -> imageEntityList.map { it.toDomain() } }

    suspend fun saveTrackedImage(trackedImage: TrackedImage) {
        withContext(ioDispatcher) {
            dao.insert(trackedImage.toEntity())
        }
    }

    suspend fun updateTrackedImage(trackedImage: TrackedImage) {
        withContext(ioDispatcher) {
            dao.update(trackedImage.toEntity())
        }
    }

    suspend fun deleteAllTrackedImage() {
        withContext(ioDispatcher) {
            dao.deleteAll()
        }
    }
}