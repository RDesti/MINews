package com.example.minews.network.parseModels

data class Image(
    val id: String?,
    val resolutions: List<Resolution>?,
    val source: Source?,
    val variants: Variants?
)