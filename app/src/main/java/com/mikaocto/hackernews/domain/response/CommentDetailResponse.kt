package com.mikaocto.hackernews.domain.response

import com.mikaocto.hackernews.data.CommentDetail
import com.mikaocto.hackernews.util.toTime

data class CommentDetailResponse(
    val `by`: String,
    val id: Int,
    val kids: List<Int>,
    val parent: Int,
    val text: String,
    val time: Int,
    val type: String
) {
    fun toCommentDetail(): CommentDetail =
        CommentDetail(
            id = id,
            text = text,
            time = time,
            by = by
        )
}