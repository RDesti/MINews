package com.example.minews.requesters

import com.example.minews.constants.LinkData
import com.example.minews.network.api.RedditApiService
import com.example.minews.network.parseModels.ContentParseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class RedditDataRequester @Inject constructor(
    private var redditApiService: RedditApiService
) : IRedditDataRequester {
    override suspend fun sendRequest(nextPageId: String?): ContentParseModel? {
        return try {
            withContext(Dispatchers.IO) {
                val url = if (nextPageId.isNullOrEmpty())
                    LinkData.REDDIT_TOP_INFO_URL
                else
                    "${LinkData.REDDIT_TOP_INFO_URL}?after=${nextPageId}"
                val result = redditApiService.getTopData(url)
                if (result.isSuccessful) {
                    return@withContext result.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}