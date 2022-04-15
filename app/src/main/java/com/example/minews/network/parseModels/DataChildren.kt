package com.example.minews.network.parseModels

data class DataChildren(
    val author: String?,
    val created_utc: Double?,
    val id: String?,
    val is_video: Boolean?,
    val media: Media?,
    val num_comments: Int?,
    val thumbnail: String?,
    val title: String?,
    val url: String?
)