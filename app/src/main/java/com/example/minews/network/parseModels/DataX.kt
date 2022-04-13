package com.example.minews.network.parseModels

data class DataX(
    val author: String?,
    val created_utc: Double?,
    val id: String?,

    val is_video: Boolean?,
    val media: SecureMedia?,

    val name: String?,
    val num_comments: Int?,

    val preview: Preview?,

    val secure_media: SecureMedia?,

    val thumbnail: String?,
    val title: String?,

    val url: String?
)