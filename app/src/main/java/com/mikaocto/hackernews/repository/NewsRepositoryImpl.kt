package com.mikaocto.hackernews.repository

import com.mikaocto.hackernews.data.CommentDetail
import com.mikaocto.hackernews.data.StoryDetail
import com.mikaocto.hackernews.domain.API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api: API) : NewsRepository {

    override suspend fun getTopStoryList(): Flow<MutableList<StoryDetail>> = flow {
        val storyList = mutableListOf<StoryDetail>()
        val newsId = api.getTopStoryList()
        if (newsId.isSuccessful) {
            newsId.body()?.let {
                for (id in it) {
                    val dataResult = api.getStoryDetail(id)
                    if (dataResult.isSuccessful)
                        dataResult.body()?.let { data -> storyList.add(data.toStoryDetail()) }
                    else throw Exception(dataResult.errorBody().toString())
                }
                emit(storyList)
            }
        } else throw Exception(newsId.errorBody().toString())
    }

    override suspend fun getCommentList(commentIdList: List<Int>): Flow<MutableList<CommentDetail>> =
        flow {
            val commentList = mutableListOf<CommentDetail>()
            for (id in commentIdList) {
                val result = api.getCommentDetail(id)
                if (result.isSuccessful)
                    result.body()?.let { data -> commentList.add(data.toCommentDetail()) }
                else throw Exception(result.errorBody().toString())
            }
            emit(commentList)
        }

    override suspend fun getStoryDetail(storyId: Int): Flow<StoryDetail> = flow {
        val result = api.getStoryDetail(storyId)
        if (result.isSuccessful)
            result.body()?.let { emit(it.toStoryDetail()) }
        else throw Exception(result.errorBody().toString())
    }
}