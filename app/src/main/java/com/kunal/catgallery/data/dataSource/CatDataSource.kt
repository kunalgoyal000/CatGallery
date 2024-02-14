package com.kunal.catgallery.data.dataSource

import com.kunal.catgallery.data.entity.Cat
import retrofit2.Response

interface CatDataSource {

   suspend fun getCats(limit: Int): Response<List<Cat>>

}