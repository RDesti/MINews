package com.example.minews.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.minews.constants.AppDefaultValues
import com.example.minews.entity.TopContentModel
import com.example.minews.network.parseModels.Children
import com.example.minews.requesters.IRedditDataRequester
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RedditPagingSource @Inject constructor(
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
            val responseModel = redditDataRequester.sendRequest(AppDefaultValues.NEXT_PAGE_KEY)
            if (responseModel == null || !responseModel.isSuccessful) {
                return LoadResult.Error(HttpException(responseModel))
            } else {
                AppDefaultValues.NEXT_PAGE_KEY = responseModel.body()?.data?.after ?: ""
                val items =
                    responseModel.body()?.data?.children
                        ?: emptyList()
                val nextKey = if (items.size < pageSize) null else page + 1

                LoadResult.Page(
                    data = items,
                    prevKey = null,
                    nextKey = nextKey
                )
            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }


}