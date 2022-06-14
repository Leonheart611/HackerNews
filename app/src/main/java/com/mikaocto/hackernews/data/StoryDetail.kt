package com.mikaocto.hackernews.data

data class StoryDetail(
    val `by`: String,
    val descendants: Int,
    val id: Int,
    val score: Int,
    val date: String,
    val title: String,
    val url: String,
    val kids: List<Int>
)
