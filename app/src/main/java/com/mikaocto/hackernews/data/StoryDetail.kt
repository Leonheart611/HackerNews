package com.mikaocto.hackernews.data

data class StoryDetail(
    val descendants: Int,
    val id: Int,
    val score: Int,
    val time: Int,
    val title: String,
    val url: String,
    val kids: List<Int>
)
