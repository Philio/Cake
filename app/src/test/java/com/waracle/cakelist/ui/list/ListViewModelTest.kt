package com.waracle.cakelist.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.waracle.cakelist.data.model.Cake
import com.waracle.cakelist.data.repository.CakeRepository
import com.waracle.cakelist.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val cakeRepository: CakeRepository = mockk()

    @Test
    fun `Given repo returns cakes When view model is instantiated Then success is emitted and cakes are sorted by title alphabetically`() {
        val data = listOf(bakeCake("C"), bakeCake("A"), bakeCake("K"), bakeCake("E"), bakeCake("S"))
        coEvery { cakeRepository.getUniqueCakes() } returns data

        mainCoroutineRule.pauseDispatcher()
        val viewModel = ListViewModel(cakeRepository)
        assertEquals(Result.Loading, viewModel.cakes.value)

        mainCoroutineRule.resumeDispatcher()
        coVerify(exactly = 1) { cakeRepository.getUniqueCakes() }
        assertEquals(Result.Success(data.sortedBy { it.title }), viewModel.cakes.value)
    }

    @Test
    fun `Given repo throws an error When view model is instantiated Then error is emitted`() {
        val exception = Exception("Something went wrong")
        coEvery { cakeRepository.getUniqueCakes() } throws exception

        mainCoroutineRule.pauseDispatcher()
        val viewModel = ListViewModel(cakeRepository)
        assertEquals(Result.Loading, viewModel.cakes.value)

        mainCoroutineRule.resumeDispatcher()
        coVerify(exactly = 1) { cakeRepository.getUniqueCakes() }
        assertEquals(Result.Error(exception), viewModel.cakes.value)
    }

    @Test
    fun `Given repo returns new cakes When refresh is called Then success is emitted with new cakes`() {
        val first = listOf(bakeCake("C"), bakeCake("A"))
        val second = listOf(bakeCake("K"), bakeCake("E"))
        coEvery { cakeRepository.getUniqueCakes() } returns first andThen second

        val viewModel = ListViewModel(cakeRepository)
        assertEquals(Result.Success(first.sortedBy { it.title }), viewModel.cakes.value)

        mainCoroutineRule.pauseDispatcher()
        viewModel.refresh()
        assertEquals(Result.Loading, viewModel.cakes.value)

        mainCoroutineRule.resumeDispatcher()
        coVerify(exactly = 2) { cakeRepository.getUniqueCakes() }
        assertEquals(Result.Success(second.sortedBy { it.title }), viewModel.cakes.value)
    }

    private fun bakeCake(title: String): Cake {
        val cake = mockk<Cake>()
        every { cake.title } returns title
        return cake
    }
}