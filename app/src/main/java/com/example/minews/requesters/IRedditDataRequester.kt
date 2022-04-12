package com.example.minews.requesters

import com.example.minews.network.parseModels.ContentParseModel

interface IRedditDataRequester {
    suspend fun sendRequest(nextPageId: String?): ContentParseModel?
}