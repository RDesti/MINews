package com.example.minews.network.api

import com.example.minews.network.parseModels.ContentParseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RedditApiService {
    @GET
    suspend fun getTopData(@Url url: String): Response<ContentParseModel>
}