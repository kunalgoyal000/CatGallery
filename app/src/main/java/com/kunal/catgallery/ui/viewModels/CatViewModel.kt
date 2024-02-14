package com.kunal.catgallery.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.catgallery.data.AppConstants
import com.kunal.catgallery.data.entity.Cat
import com.kunal.catgallery.ui.repository.CatRepository
import com.kunal.catgallery.utils.DispatcherProvider
import com.kunal.catgallery.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val catRepository: CatRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _cats: MutableStateFlow<ResourceState<List<Cat>>> =
        MutableStateFlow(ResourceState.Loading())
    val cats: StateFlow<ResourceState<List<Cat>>> = _cats

    init {
        getCats(AppConstants.LIMIT)
    }

    fun getCats(limit: Int) {
        viewModelScope.launch(dispatcherProvider.io) {
            catRepository.getCats(limit)
                .collectLatest { catsResponse ->
                    _cats.value = catsResponse
                }
        }
    }

    companion object {
        const val TAG = "CatViewModel"
    }

}