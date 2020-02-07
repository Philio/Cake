package com.waracle.cakelist.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.waracle.cakelist.data.model.Cake
import com.waracle.cakelist.data.repository.CakeRepository
import com.waracle.cakelist.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    private val cakeRepository: CakeRepository = mockk()

    @Test
    fun `Given repo returns cakes When view model is instantiated Then success is emitted and cakes are sorted by title alphabetically`() =
        runBlockingTest {
            val data = listOf(bakeCake("C"), bakeCake("A"), bakeCake("K"), bakeCake("E"), bakeCake("S"))
            coEvery { cakeRepository.getUniqueCakes() } returns data

            coroutineTestRule.pauseDispatcher()
            val viewModel = ListViewModel(cakeRepository)
            val collection = async { viewModel.cakes.asFlow().take(2).toList() }
            coroutineTestRule.runCurrent()

            assertEquals(
                listOf(Result.Loading, Result.Success(data.sortedBy { it.title })),
                collection.await()
            )
        }

    @Test
    fun `Given repo throws an error When view model is instantiated Then error is emitted`() =
        runBlockingTest {
            val exception = Exception("Something went wrong")
            coEvery { cakeRepository.getUniqueCakes() } throws exception

            coroutineTestRule.pauseDispatcher()
            val viewModel = ListViewModel(cakeRepository)
            val collection = async { viewModel.cakes.asFlow().take(2).toList() }
            coroutineTestRule.runCurrent()

            assertEquals(listOf(Result.Loading, Result.Error(exception)), collection.await())
        }

    private fun bakeCake(title: String): Cake {
        val cake = mockk<Cake>()
        every { cake.title } returns title
        return cake
    }
}