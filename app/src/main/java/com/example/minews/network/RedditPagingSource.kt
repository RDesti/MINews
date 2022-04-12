package com.example.minews.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.minews.network.parseModels.Children
import com.example.minews.requesters.IRedditDataRequester
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException


class RedditPagingSource @AssistedInject constructor(
    private val redditDataRequester: IRedditDataRequester
) : PagingSource<Int, Children>() {

    override fun getRefreshKey(state: PagingState<Int, Children>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Children> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val responseModel = redditDataRequester.sendRequest(page.toString())
            val items = responseModel?.data?.children ?: emptyList()
            val nextKey = if (items.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                data = items,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): RedditPagingSource
    }
}