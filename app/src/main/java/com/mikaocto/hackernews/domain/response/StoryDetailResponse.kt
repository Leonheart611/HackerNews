package com.mikaocto.hackernews.domain.response

import com.mikaocto.hackernews.data.StoryDetail
import com.mikaocto.hackernews.util.toTime

data class StoryDetailResponse(
    val `by`: String,
    val descendants: Int?,
    val id: Int?,
    val kids: List<Int>?,
    val score: Int?,
    val time: Int?,
    val title: String?,
    val type: String?,
    val url: String?
) {
    fun toStoryDetail() = StoryDetail(
        descendants = descendants ?: 0,
        id = id ?: 0,
        score = score ?: 0,
        date = time?.toTime().orEmpty(),
        title = title.orEmpty(),
        url = url.orEmpty(),
        kids = kids ?: mutableListOf(),
        by = by
    )
}