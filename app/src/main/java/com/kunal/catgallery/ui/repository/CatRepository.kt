package com.kunal.catgallery.ui.repository

import com.kunal.catgallery.data.dataSource.CatDataSource
import com.kunal.catgallery.data.entity.Cat
import com.kunal.catgallery.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val catDataSource: CatDataSource
) {

//    suspend fun getCats(limit: Int): List<Cat>{
//        return catDataSource.getCats(limit)
//    }

    suspend fun getCats(limit: Int): Flow<ResourceState<List<Cat>>> {
        return flow {
            emit(ResourceState.Loading())

            val response = catDataSource.getCats(limit)

            if(response.isSuccessful && response.body() != null){
                emit(ResourceState.Success(response.body()!!))
            }else{
                emit(ResourceState.Error("Error fetching data"))
            }
        }.catch { e ->
            emit(ResourceState.Error(e.localizedMessage ?:"Some error in flow"))
        }
    }
}