package com.mikaocto.hackernews.data

data class CommentDetail(
    val `by`: String,
    val id: Int,
    val text: String,
    val time: Int
)