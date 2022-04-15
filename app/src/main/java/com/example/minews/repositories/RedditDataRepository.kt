package com.example.minews.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.minews.constants.AppDefaultValues
import com.example.minews.network.parseModels.Children
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedditDataRepository @Inject constructor(
    private val redditPagingSource: RedditPagingSource
) : IRedditDataRepository {

    override fun getDataFromReddit(): Flow<PagingData<Children>> {
        return Pager(
            config = PagingConfig(
                pageSize = AppDefaultValues.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { redditPagingSource }
        ).flow
    }

}