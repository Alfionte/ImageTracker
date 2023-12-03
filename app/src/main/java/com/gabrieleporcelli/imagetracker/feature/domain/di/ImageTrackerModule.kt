package com.gabrieleporcelli.imagetracker.feature.domain.di

import com.gabrieleporcelli.imagetracker.feature.data.TrackedImageRepositoryImpl
import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.DeleteAllTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.DeleteAllTrackedImageUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.GetTrackedImageStreamUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.GetTrackedImageStreamUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.SaveTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.SaveTrackedImageUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UpdateTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UpdateTrackedImageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ImageTrackerModule {

    @Binds
    abstract fun bindsTrackerImageRepository(
        trackedImageRepositoryImpl: TrackedImageRepositoryImpl
    ): TrackedImageRepository

    @Binds
    abstract fun bindsGetTrackedImageStreamUseCase(
        getTrackedImageStreamUseCaseImpl: GetTrackedImageStreamUseCaseImpl
    ): GetTrackedImageStreamUseCase

    @Binds
    abstract fun bindsSaveTrackedImageUseCase(
        saveTrackedImageUseCaseImpl: SaveTrackedImageUseCaseImpl
    ): SaveTrackedImageUseCase

    @Binds
    abstract fun bindsUpdateTrackedImageUseCase(
        updateTrackedImageUseCaseImpl: UpdateTrackedImageUseCaseImpl
    ): UpdateTrackedImageUseCase

    @Binds
    abstract fun bindsDeleteAllTrackedImageUseCase(
        deleteAllTrackedImageUseCaseImpl: DeleteAllTrackedImageUseCaseImpl
    ): DeleteAllTrackedImageUseCase
}