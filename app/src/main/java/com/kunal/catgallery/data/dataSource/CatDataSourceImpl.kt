package com.kunal.catgallery.data.dataSource

import com.kunal.catgallery.data.api.ApiService
import com.kunal.catgallery.data.entity.Cat
import retrofit2.Response
import javax.inject.Inject

class CatDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): CatDataSource {

    override suspend fun getCats(limit: Int): Response<List<Cat>> {
        return apiService.getCats(limit)
    }
}