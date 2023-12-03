@file:Suppress("unused")

package com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.di

import com.gabrieleporcelli.imagetracker.core.dispatcher.AppDispatcher
import com.gabrieleporcelli.imagetracker.core.environments.Environment
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.FlickrApi
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.TrackerImageRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        httpClient.addInterceptor(interceptor)
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideFlickrService(environment: Environment, okHttpClient: OkHttpClient): FlickrApi =
        Retrofit.Builder()
            .baseUrl(environment.flickrBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FlickrApi::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        service: FlickrApi,
        dispatcher: AppDispatcher
    ): TrackerImageRemoteDataSource =
        TrackerImageRemoteDataSource(service, dispatcher.io)
}