package com.gabrieleporcelli.imagetracker.feature.domain.usecases

import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrPhotoDetail
import javax.inject.Inject

interface UrlBuilderUseCase {
    suspend fun buildUrl(flickrPhotoDetail: FlickrPhotoDetail?, requestSize: RequestSize? = null): String?
}

class UrlBuilderUseCaseImpl @Inject constructor() : UrlBuilderUseCase {
    override suspend fun buildUrl(flickrPhotoDetail: FlickrPhotoDetail?, requestSize: RequestSize?): String? {
        return flickrPhotoDetail?.photo?.let { safePhoto ->
            val size = if (requestSize != null) "_${requestSize.value}" else ""
            "https://farm${safePhoto.farmId}.staticflickr.com/${safePhoto.serverId}/${safePhoto.id}_${safePhoto.secret}${size}.jpg"
        }
    }
}

@Suppress("unused")
enum class RequestSize(val value: String) {
    Thumbnail75Square("s"),
    Thumbnail150Square("q"),
    Thumbnail100("t"),
    Small240("m"),
    Small320("n"),
    Small400("w"),
    Medium640("z"),
    Medium800("c"),
    Large1024("b");
    // The rest has a unique secret; photo owner can restrict
}