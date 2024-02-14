package com.kunal.catgallery.ui.viewModels

import app.cash.turbine.test
import com.kunal.catgallery.data.entity.Cat
import com.kunal.catgallery.ui.repository.CatRepository
import com.kunal.catgallery.utils.DispatcherProvider
import com.kunal.catgallery.utils.ResourceState
import com.kunal.catgallery.utils.TestDispatcherProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CatViewModelTest {

    @Mock
    private lateinit var catRepository: CatRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    private var testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcherProvider = TestDispatcherProvider()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() = runTest {
        doReturn(flowOf(emptyList<Cat>())).`when`(catRepository).getCats(10)
        val viewModel = CatViewModel(catRepository, dispatcherProvider)
        viewModel.cats.test {
            assertEquals(ResourceState.Success(emptyList<List<Cat>>()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify(catRepository).getCats(10)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}