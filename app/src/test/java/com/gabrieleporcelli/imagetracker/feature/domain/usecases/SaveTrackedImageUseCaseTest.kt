package com.gabrieleporcelli.imagetracker.feature.domain.usecases

import com.gabrieleporcelli.imagetracker.feature.domain.repository.TrackedImageRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveTrackedImageUseCaseTest {

    private val repository = mockk<TrackedImageRepository>()
    private lateinit var saveTrackedImageUseCase: SaveTrackedImageUseCase

    @Before
    fun setUp() {
        saveTrackedImageUseCase = SaveTrackedImageUseCaseImpl(
            repository = repository
        )
    }

    @Test
    fun `when saving tracked image, invoke repository save`() = runTest {
        coEvery { repository.saveTrackedImage(any()) } just runs
        saveTrackedImageUseCase.saveTrackedImage(mockk())
        coVerify { repository.saveTrackedImage(any()) }
    }
}