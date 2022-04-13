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
                            tmpModel =
                                it.data.created_utc?.let { it4 -> formattedDate(it4) }?.let { it5 ->
                                    TopContentModel(
                                        it1,
                                        it2,
                                        it5,
                                        it3,
                                        it.data.thumbnail,
                                        if (!it.data.is_video!!) it.data.url else it.data.media?.reddit_video?.fallback_url?.replace(
                                            "?source=fallback",
                                            ""
                                        ),
                                        it.data.num_comments.toString()
                                    )
                                }
                        }
                    }
                }
            }
            listModel.add(tmpModel ?: return@forEach)
        }
        return listModel
    }

    private fun formattedDate(date: Double): String {
        val currentTime = System.currentTimeMillis().toDouble()
        val timeAgo = (currentTime - date * 1000) / 3600000
        return if (timeAgo > 0) "${timeAgo.toInt()} hours ago"
        else "now"
    }
}