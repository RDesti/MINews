package com.example.minews.requesters

import com.example.minews.network.parseModels.ContentParseModel
import retrofit2.Response

interface IRedditDataRequester {
    suspend fun sendRequest(nextPageId: String?): Response<ContentParseModel>?
}