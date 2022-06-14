package com.mikaocto.hackernews.domain.response

import com.mikaocto.hackernews.data.StoryDetail

data class StoryDetailResponse(
    val `by`: String,
    val descendants: Int,
    val id: Int,
    val kids: List<Int>,
    val score: Int,
    val time: Int,
    val title: String,
    val type: String,
    val url: String
) {
    fun toStoryDetail() = StoryDetail(
        descendants = descendants,
        id = id,
        score = score,
        time = time,
        title = title,
        url = url,
        kids = kids
    )
}