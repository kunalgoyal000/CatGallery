package com.kunal.catgallery.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.catgallery.data.AppConstants
import com.kunal.catgallery.data.entity.Cat
import com.kunal.catgallery.ui.repository.CatRepository
import com.kunal.catgallery.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val catRepository: CatRepository
) : ViewModel() {

    private val _cats: MutableStateFlow<ResourceState<List<Cat>>> = MutableStateFlow(ResourceState.Loading())
    val cats: StateFlow<ResourceState<List<Cat>>> = _cats

    init {
        getCats(AppConstants.LIMIT)
    }

    private fun getCats(limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            catRepository.getCats(limit)
                .collectLatest { catsResponse ->
                    _cats.value = catsResponse
                }
        }
    }

    companion object{
        const val TAG = "CatViewModel"
    }

}