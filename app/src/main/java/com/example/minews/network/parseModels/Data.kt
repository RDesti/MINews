package com.example.minews.network.parseModels

data class Data(
    val after: String?,
    val before: Any?,
    val children: List<Children>?,
    val dist: Int?,
    val geo_filter: String?,
    val modhash: String?
)