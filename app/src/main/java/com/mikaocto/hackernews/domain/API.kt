package com.mikaocto.hackernews.domain

import com.mikaocto.hackernews.domain.response.StoryDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("/topstories.json")
    suspend fun getTopStoryList(): Response<List<Int>>

    @GET("/item/{id}.json")
    suspend fun getStoryDetail(@Path("id") id: Int): Response<StoryDetail>

}