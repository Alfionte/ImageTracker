package com.gabrieleporcelli.imagetracker.feature.data.remotedatasource

import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrPhotoDetail
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrResponse
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TrackerImageRemoteDataSource(
    private val service: FlickrApi,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun fetchFlickrPhotos(position: LatLng): Result<FlickrResponse> =
        withContext(ioDispatcher) {
            runCatching {
                service.fetchFlickrPhotos(
                    apiKey = FLICKR_API_KEY,
                    method = FLICKR_METHOD_PHOTO_SEARCH,
                    lat = position.latitude,
                    lng = position.longitude,
                    radius = RADIUS,
                    contentType = CONTENT_TYPE_PHOTO,
                    privacy = PRIVACY_FILTER_PUBLIC,
                    perPage = REQUESTED_PER_PAGE,
                    page = REQUESTED_PAGES,
                    format = JSON_FORMAT,
                    noJsonCallBackMode = NO_JSON_CALLBACK,
                )
            }
        }

    suspend fun fetchFlickrPhoto(id: String): Result<FlickrPhotoDetail> =
        withContext(ioDispatcher) {
            runCatching {
                service.fetchFlickrPhoto(
                    apiKey = FLICKR_API_KEY,
                    method = FLICKR_METHOD_PHOTO_GET_INFO,
                    id = id,
                    format = JSON_FORMAT,
                    noJsonCallBackMode = NO_JSON_CALLBACK,
                )
            }
        }

    companion object {
        const val FLICKR_METHOD_PHOTO_SEARCH = "flickr.photos.search"
        const val FLICKR_METHOD_PHOTO_GET_INFO = "flickr.photos.getInfo"
        const val FLICKR_API_KEY = "c42615c726f6e906472df4c9b3cb5281"
        const val RADIUS = 1 //km
        const val JSON_FORMAT = "json"
        const val NO_JSON_CALLBACK = 1
        const val REQUESTED_PAGES = 1
        const val REQUESTED_PER_PAGE = 5
        const val PRIVACY_FILTER_PUBLIC = 1
        const val CONTENT_TYPE_PHOTO = 0
    }
}