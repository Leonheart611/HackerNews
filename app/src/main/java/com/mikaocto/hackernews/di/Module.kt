package com.mikaocto.hackernews.di

import com.mikaocto.hackernews.domain.API
import com.mikaocto.hackernews.repository.NewsRepository
import com.mikaocto.hackernews.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class Module {
    private val baseUrl = "https://hacker-news.firebaseio.com/v0"

    @Provides
    fun provideApiService(): API {
        val retrofit: Retrofit

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .cache(null)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(API::class.java)
    }

    @Provides
    fun provideNewsRepository(api: API): NewsRepository = NewsRepositoryImpl(api)

}