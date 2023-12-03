package com.gabrieleporcelli.imagetracker.feature.domain.repository

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrPhotoDetail
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrResponse
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface TrackedImageRepository {

    suspend fun fetchTrackedImagesByLocation(position: LatLng): Result<FlickrResponse>
    suspend fun fetchTrackedImage(id: String): Result<FlickrPhotoDetail>

    fun getTrackedImageStream(): Flow<List<TrackedImage>>

    suspend fun saveTrackedImage(trackedImage: TrackedImage)

    suspend fun updateTrackedImage(trackedImage: TrackedImage)

    suspend fun deleteAllTrackedImage()
}