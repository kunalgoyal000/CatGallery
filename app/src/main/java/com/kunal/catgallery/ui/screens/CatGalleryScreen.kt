package com.kunal.catgallery.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.kunal.catgallery.ui.components.CatList
import com.kunal.catgallery.ui.components.Loader
import com.kunal.catgallery.ui.viewModel.CatViewModel
import com.kunal.catgallery.utils.ResourceState

@Composable
fun CatGalleryScreen(viewModel: CatViewModel = hiltViewModel()) {

    val catsRes by viewModel.cats.collectAsState()

    when(catsRes){
        is ResourceState.Loading ->{
            Loader()
        }

        is ResourceState.Success ->{
            val response = (catsRes as ResourceState.Success).data
            CatList(response)
        }

        is ResourceState.Error ->{
            val error = (catsRes as ResourceState.Error)
        }
    }
}
