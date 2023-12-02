@file:Suppress("unused")

package com.gabrieleporcelli.imagetracker.application.di

import com.gabrieleporcelli.imagetracker.core.dispatcher.AppDispatcher
import com.gabrieleporcelli.imagetracker.core.dispatcher.AppDispatcherDefault
import com.gabrieleporcelli.imagetracker.core.environments.Environment
import com.gabrieleporcelli.imagetracker.core.environments.EnvironmentDefault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEnvironment(): Environment = EnvironmentDefault()

    @Singleton
    @Provides
    fun bindsAppDispatcher(): AppDispatcher = AppDispatcherDefault()
}