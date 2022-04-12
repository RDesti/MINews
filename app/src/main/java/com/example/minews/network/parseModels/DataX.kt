package com.example.minews.network.parseModels

data class DataX(
    val author: String?,
    val created_utc: Double?,


    val id: String?,

    val is_self: Boolean?,
    val is_video: Boolean?,
    val likes: Any?,
    val locked: Boolean?,
    val media: Any?,
    val media_embed: MediaEmbed?,
    val media_only: Boolean?,

    val name: String?,
    val num_comments: Int?,
    val num_crossposts: Int?,

    val preview: Preview?,

    val score: Int?,
    val secure_media: Any?,
    val secure_media_embed: SecureMediaEmbed?,
    val selftext: String?,
    val selftext_html: Any?,

    val thumbnail: String?,
    val thumbnail_height: Int?,
    val thumbnail_width: Int?,
    val title: String?,

    val url: String?,
    val url_overridden_by_dest: String?,
)