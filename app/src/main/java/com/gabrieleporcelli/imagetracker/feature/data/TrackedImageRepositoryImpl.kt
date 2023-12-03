package com.gabrieleporcelli.imagetracker.feature.data

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageLocalDataSource
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.TrackerImageRemoteDataSource
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrPhotoDetail
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrResponse
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackedImageRepositoryImpl @Inject constructor(
    private val localDataSource: TrackerImageLocalDataSource,
    private val remoteDataSource: TrackerImageRemoteDataSource
) : TrackedImageRepository {
    override suspend fun fetchTrackedImagesByLocation(position: LatLng): Result<FlickrResponse> =
        remoteDataSource.fetchFlickrPhotos(position)

    override suspend fun fetchTrackedImage(id: String): Result<FlickrPhotoDetail> =
        remoteDataSource.fetchFlickrPhoto(id)

    override fun getTrackedImageStream(): Flow<List<TrackedImage>> =
        localDataSource.getTrackedImageStream()

    override suspend fun saveTrackedImage(trackedImage: TrackedImage) =
        localDataSource.saveTrackedImage(trackedImage)

    override suspend fun updateTrackedImage(trackedImage: TrackedImage) =
        localDataSource.updateTrackedImage(trackedImage)

    override suspend fun deleteAllTrackedImage() =
        localDataSource.deleteAllTrackedImage()

}