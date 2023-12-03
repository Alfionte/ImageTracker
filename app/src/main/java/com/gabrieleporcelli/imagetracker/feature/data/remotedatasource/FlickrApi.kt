package com.gabrieleporcelli.imagetracker.feature.data.remotedatasource

import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrPhotoDetail
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.model.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrApi {

    @GET("services/rest/")
    suspend fun fetchFlickrPhotos(
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lng: Double,
        @Query("radius") radius: Int,
        @Query("content_types") contentType: Int,
        @Query("privacy_filter") privacy: Int,
        @Query("nojsoncallback") noJsonCallBackMode: Int,
        @Query("format") format: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): FlickrResponse


    @GET("services/rest/")
    suspend fun fetchFlickrPhoto(
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("photo_id") id: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallBackMode: Int,
    ): FlickrPhotoDetail

}