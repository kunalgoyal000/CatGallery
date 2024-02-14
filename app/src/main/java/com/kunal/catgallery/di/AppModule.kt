package com.kunal.catgallery.di

import com.kunal.catgallery.data.AppConstants
import com.kunal.catgallery.data.api.ApiService
import com.kunal.catgallery.data.dataSource.CatDataSource
import com.kunal.catgallery.data.dataSource.CatDataSourceImpl
import com.kunal.catgallery.ui.repository.CatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit{

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }

        httpClient.apply {
            readTimeout(60,TimeUnit.SECONDS)
        }

        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCatDataSource(apiService: ApiService): CatDataSource {
        return CatDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesCatRepository(catDataSource: CatDataSource): CatRepository {
        return CatRepository(catDataSource)
    }
}