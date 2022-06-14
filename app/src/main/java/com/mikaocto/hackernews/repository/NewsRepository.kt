package com.mikaocto.hackernews.repository

import com.mikaocto.hackernews.data.CommentDetail
import com.mikaocto.hackernews.data.StoryDetail
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopStoryList(): Flow<MutableList<StoryDetail>>
    suspend fun getCommentList(commentIdList: List<Int>): Flow<MutableList<CommentDetail>>
    suspend fun getStoryDetail(storyId: Int): Flow<StoryDetail>
}