package com.example.minews.network

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
) : PagingSource<Int, TopContentModel>() {

    override fun getRefreshKey(state: PagingState<Int, TopContentModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopContentModel> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val responseModel = redditDataRequester.sendRequest(AppDefaultValues.NEXT_PAGE_KEY)
            AppDefaultValues.NEXT_PAGE_KEY = responseModel?.data?.after ?: ""
            val items =
                responseModel?.data?.children?.let { createTopContentModel(it) } ?: emptyList()
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

    private fun createTopContentModel(model: List<Children>): List<TopContentModel> {
        val listModel = mutableListOf<TopContentModel>()
        var tmpModel: TopContentModel? = null
        model.forEach {
            if (it.data != null) {
                it.data.id?.let { it1 ->
                    it.data.author?.let { it2 ->
                        it.data.title?.let { it3 ->
                            tmpModel = TopContentModel(
                                it1,
                                it2, it.data.created_utc.toString(),
                                it3,
                                it.data.thumbnail,
                                it.data.url,
                                it.data.num_comments.toString()
                            )
                        }
                    }
                }
            }
            listModel.add(tmpModel ?: return@forEach)
        }
        return listModel
    }
}