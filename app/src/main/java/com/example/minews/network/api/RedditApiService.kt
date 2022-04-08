package com.example.minews.network.api

import com.example.minews.constants.LinkData
import com.example.minews.network.parseModels.ContentParseModel
import retrofit2.Response
import retrofit2.http.GET

interface RedditApiService {
    @GET(LinkData.REDDIT_TOP_INFO_URL)
    suspend fun getTopData(): Response<ContentParseModel>
}