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

class UpdateTrackedImageUseCaseTest {

    private val repository = mockk<TrackedImageRepository>()
    private lateinit var updateTrackedImageUseCase: UpdateTrackedImageUseCase

    @Before
    fun setUp() {
        updateTrackedImageUseCase = UpdateTrackedImageUseCaseImpl(
            repository = repository
        )
    }

    @Test
fun `when updating tracked image, invoke repository update`() = runTest {
        coEvery { repository.updateTrackedImage(any()) } just runs
        updateTrackedImageUseCase.updateTrackedImage(mockk())
        coVerify { repository.updateTrackedImage(any()) }
    }
}