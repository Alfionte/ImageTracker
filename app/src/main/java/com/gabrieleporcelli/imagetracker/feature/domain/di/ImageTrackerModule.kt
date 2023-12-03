@file:Suppress("unused")

package com.gabrieleporcelli.imagetracker.feature.domain.di

import com.gabrieleporcelli.imagetracker.feature.domain.usecases.DeleteAllTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.DeleteAllTrackedImageUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.FetchTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.FetchTrackedImageUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.GetTrackedImageStreamUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.GetTrackedImageStreamUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UpdateTrackedImageUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UpdateTrackedImageUseCaseImpl
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UrlBuilderUseCase
import com.gabrieleporcelli.imagetracker.feature.domain.usecases.UrlBuilderUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ImageTrackerModule {

    @Binds
    abstract fun bindsGetTrackedImageStreamUseCase(
        getTrackedImageStreamUseCaseImpl: GetTrackedImageStreamUseCaseImpl
    ): GetTrackedImageStreamUseCase

    @Binds
    abstract fun bindsUpdateTrackedImageUseCase(
        updateTrackedImageUseCaseImpl: UpdateTrackedImageUseCaseImpl
    ): UpdateTrackedImageUseCase

    @Binds
    abstract fun bindsDeleteAllTrackedImageUseCase(
        deleteAllTrackedImageUseCaseImpl: DeleteAllTrackedImageUseCaseImpl
    ): DeleteAllTrackedImageUseCase

    @Binds
    abstract fun fetchTrackedImageUseCase(
        fetchTrackedImageUseCaseImpl: FetchTrackedImageUseCaseImpl
    ): FetchTrackedImageUseCase

    @Binds
    abstract fun bindUrlBuilderUseCase(
        urlBuilderUseCaseImpl: UrlBuilderUseCaseImpl
    ): UrlBuilderUseCase
}