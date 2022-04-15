package com.example.minews.requesters

import com.example.minews.constants.LinkData
import com.example.minews.network.api.RedditApiService
import com.example.minews.network.parseModels.ContentParseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class RedditDataRequester @Inject constructor(
    private var redditApiService: RedditApiService
) : IRedditDataRequester {
    override suspend fun sendRequest(nextPageId: String?): Response<ContentParseModel>? {
        return try {
            withContext(Dispatchers.IO) {
                val url = if (nextPageId.isNullOrEmpty())
                    LinkData.REDDIT_TOP_INFO_URL
                else
                    "${LinkData.REDDIT_TOP_INFO_URL}?after=${nextPageId}"
                return@withContext redditApiService.getTopData(url)
            }
        } catch (e: Exception) {
            return e.message?.toResponseBody()?.let { Response.error(e.hashCode(), it) }
        }
    }
}