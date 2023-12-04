package com.gabrieleporcelli.imagetracker.feature.domain.usecases

import com.gabrieleporcelli.imagetracker.core.domain.model.TrackedImage
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

interface FetchTrackedImageUseCase {
    suspend fun fetchTrackedImage(position: LatLng, creationTime: Long): Result<TrackedImage>
}

class FetchTrackedImageUseCaseImpl @Inject constructor(
    private val repository: TrackedImageRepository,
    private val urlBuilderUseCase: UrlBuilderUseCase,
) : FetchTrackedImageUseCase {
    override suspend fun fetchTrackedImage(position: LatLng, creationTime: Long): Result<TrackedImage> {
        val responsePage = repository.fetchTrackedImagesByLocation(position)
        val imageId = responsePage.getOrNull()?.page?.photos?.first()?.id
        return if (imageId == null) {
            Result.failure(Exception("No image found"))
        } else {
            val image = repository.fetchTrackedImage(imageId).getOrNull()
            val url = urlBuilderUseCase.buildUrl(image, RequestSize.Medium640)
            val trackedImage = TrackedImage(position, url, creationTime)
            Result.success(trackedImage)
        }
    }
}