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

class DeleteAllTrackedImageUseCaseTest {

    private val repository = mockk<TrackedImageRepository>()
    private lateinit var deleteAllTrackedImageUseCase: DeleteAllTrackedImageUseCase

    @Before
    fun setUp() {
        deleteAllTrackedImageUseCase = DeleteAllTrackedImageUseCaseImpl(
            repository = repository
        )
    }

    @Test
    fun `when deleting all tracked images, invoke repository delete all`() = runTest {
        coEvery { repository.deleteAllTrackedImage() } just runs
        deleteAllTrackedImageUseCase.deleteAllTrackedImage()
        coVerify { repository.deleteAllTrackedImage() }
    }
}