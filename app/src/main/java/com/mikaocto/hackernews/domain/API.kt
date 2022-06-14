package com.mikaocto.hackernews.domain

import com.mikaocto.hackernews.domain.response.CommentDetailResponse
import com.mikaocto.hackernews.domain.response.StoryDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("/topstories.json")
    suspend fun getTopStoryList(): Response<List<Int>>

    @GET("/item/{id}.json")
    suspend fun getStoryDetail(@Path("id") id: Int): Response<StoryDetailResponse>

    @GET("/item/{id}.json")
    suspend fun getCommentDetail(@Path("id") id: Int): Response<CommentDetailResponse>

}