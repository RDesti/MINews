package com.example.minews.entity

data class TopContentModel(
    var id: String,
    var autor: String,
    var timeAgo: String,
    var title: String,
    var thumbnail: String? = null,
    var fullImage: String? = null,
    var commentsCount: String?
) {
    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass) {
            return false
        }
        other as TopContentModel
        if (id != other.id) {
            return false
        }
        if (autor != other.autor) {
            return false
        }
        if (timeAgo != other.timeAgo) {
            return false
        }
        if (title != other.title) {
            return false
        }
        if (thumbnail != other.thumbnail) {
            return false
        }
        if (fullImage != other.fullImage) {
            return false
        }
        if (commentsCount != other.commentsCount) {
            return false
        }
        return true
    }
}
