package com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model

import com.google.gson.annotations.SerializedName

data class FlickrResponse(
    @SerializedName("photos") val page: FlickrResponsePage,
)

data class FlickrResponsePage(
    @SerializedName("page") val pageNumber: Int,
    @SerializedName("pages") val pageCount: Int,
    @SerializedName("perpage") val perPage: Int,
    @SerializedName("total") val totalPhotos: Int,
    @SerializedName("photo") val photos: List<FlickrPhoto>
)

data class FlickrPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("isPublic") val isPublic: Int,
)

data class FlickrPhotoDetail(
    @SerializedName("photo") val photo: FlickrPhotoDetailContent,
)

data class FlickrPhotoDetailContent(
    @SerializedName("id") val id: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val serverId: String,
    @SerializedName("farm") val farmId: Int,
    @SerializedName("title") val title: FlickrContent,
    @SerializedName("description") val description: FlickrContent,
    @SerializedName("isPublic") val isPublic: Int,
    @SerializedName("urls") val urls: FlickrUrls,
)

data class FlickrContent(
    @SerializedName("_content") val content: String,
)

data class FlickrUrls(
    @SerializedName("url") val urls: List<FlickrUrlContent>,
)

data class FlickrUrlContent(
    @SerializedName("type") val type: String,
    @SerializedName("_content") val url: String,
)