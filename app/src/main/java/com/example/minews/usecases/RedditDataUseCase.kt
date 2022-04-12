package com.example.minews.usecases

import com.example.minews.repositories.RedditDataRepository
import javax.inject.Inject

class RedditDataUseCase @Inject constructor(
    private val redditDataRepository: RedditDataRepository
) {

}