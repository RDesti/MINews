package com.example.minews.repositories

import com.example.minews.network.api.RedditApiService
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedditDataRepository @Inject constructor(
    private val redditApiService: RedditApiService
) {
    suspend fun sendRequestRedditData() {
        try {
            val result = redditApiService.getTopData()
            if (result.isSuccessful) {
                val m = ""
            } else {
                val g = ""
            }
        } catch (e: Exception) {

        }
    }
}