package com.mikaocto.hackernews.di

import android.content.Context
import android.content.SharedPreferences
import com.mikaocto.hackernews.domain.API
import com.mikaocto.hackernews.repository.NewsRepository
import com.mikaocto.hackernews.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {
    companion object{
        const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
        const val PREFERENCES_FILE_KEY = "STRYGWYR"
    }

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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(API::class.java)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            PREFERENCES_FILE_KEY, Context.MODE_PRIVATE
        )


    @Provides
    fun provideNewsRepository(api: API): NewsRepository = NewsRepositoryImpl(api)

}