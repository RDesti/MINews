package com.example.minews.repositories

import com.example.minews.requesters.IRedditDataRequester
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedditDataRepository @Inject constructor(
    private val redditDataRequester: IRedditDataRequester
) {

}