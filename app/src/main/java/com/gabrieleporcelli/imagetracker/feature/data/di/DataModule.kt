@file:Suppress("unused")

package com.gabrieleporcelli.imagetracker.feature.data.di

import android.app.Application
import androidx.room.Room
import com.gabrieleporcelli.imagetracker.core.dispatcher.AppDispatcher
import com.gabrieleporcelli.imagetracker.core.environments.Environment
import com.gabrieleporcelli.imagetracker.feature.data.TrackedImageRepositoryImpl
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageDao
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageDatabase
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageLocalDataSource
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.FlickrApi
import com.gabrieleporcelli.imagetracker.feature.data.remotedatasource.TrackerImageRemoteDataSource
import com.gabrieleporcelli.imagetracker.feature.domain.location.LocationClient
import com.gabrieleporcelli.imagetracker.feature.domain.location.LocationClientImpl
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.FetchTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.FetchTrackedImageUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.SaveTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.SaveTrackedImageUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UrlBuilderUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UrlBuilderUseCaseImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
object DataModule {

    @Singleton
    @Provides
    fun provideCharacterDb(applicationContext: Application): TrackerImageDatabase =
        Room.databaseBuilder(
            applicationContext, TrackerImageDatabase::class.java, "tracked-image-db"
        ).build()

    @Singleton
    @Provides
    fun provideCharacterDao(db: TrackerImageDatabase): TrackerImageDao = db.charactersDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(
        dao: TrackerImageDao,
        dispatcher: AppDispatcher
    ): TrackerImageLocalDataSource = TrackerImageLocalDataSource(dao, dispatcher.io)

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

    @Singleton
    @Provides
    fun provideFusedLocationClient(applicationContext: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(applicationContext)

    @Singleton
    @Provides
    fun provideLocationClient(
        applicationContext: Application,
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationClient =
        LocationClientImpl(applicationContext, fusedLocationProviderClient)

    @Singleton
    @Provides
    fun provideTrackerImageRepository(
        localDataSource: TrackerImageLocalDataSource,
        remoteDataSource: TrackerImageRemoteDataSource
    ): TrackedImageRepository =
        TrackedImageRepositoryImpl(localDataSource, remoteDataSource)

    @Singleton
    @Provides
    fun provideSaveTrackedImageUseCase(repository: TrackedImageRepository): SaveTrackedImageUseCase =
        SaveTrackedImageUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideUrlBuilderUseCase(): UrlBuilderUseCase = UrlBuilderUseCaseImpl()


    @Singleton
    @Provides
    fun provideFetchTrackedImageUseCase(
        repository: TrackedImageRepository,
        urlBuilderUseCase: UrlBuilderUseCase
    ): FetchTrackedImageUseCase =
        FetchTrackedImageUseCaseImpl(repository, urlBuilderUseCase)


}