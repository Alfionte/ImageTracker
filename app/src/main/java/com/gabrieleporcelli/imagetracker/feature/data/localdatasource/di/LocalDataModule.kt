@file:Suppress("unused")

package com.gabrieleporcelli.imagetracker.feature.data.localdatasource.di

import android.app.Application
import androidx.room.Room
import com.gabrieleporcelli.imagetracker.core.dispatcher.AppDispatcher
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageDao
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageDatabase
import com.gabrieleporcelli.imagetracker.feature.data.localdatasource.TrackerImageLocalDataSource
import com.gabrieleporcelli.imagetracker.feature.domain.location.LocationClient
import com.gabrieleporcelli.imagetracker.feature.domain.location.LocationClientImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

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
    fun provideFusedLocationClient(applicationContext: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(applicationContext)

    @Singleton
    @Provides
    fun provideLocationClient(
        applicationContext: Application,
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationClient =
        LocationClientImpl(applicationContext, fusedLocationProviderClient)
}