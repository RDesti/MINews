package com.example.minews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.minews.constants.AppDefaultValues
import com.example.minews.entity.TopContentModel
import com.example.minews.network.parseModels.Children
import com.example.minews.repositories.IRedditDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditDataRepository: IRedditDataRepository
) : ViewModel() {
    private var contentPagingData: Flow<PagingData<TopContentModel>>? = null

    fun getData(): Flow<PagingData<TopContentModel>> {
        if (contentPagingData == null) {
            contentPagingData = redditDataRepository.getDataFromReddit()
                .map { pagingData -> pagingData.map { createTopContentModel(it)!! } }
                .cachedIn(viewModelScope)
        }
        return contentPagingData as Flow<PagingData<TopContentModel>>
    }

    private fun createTopContentModel(model: Children): TopContentModel? {
        if (model.data != null) {
            model.data.id?.let { it1 ->
                model.data.author?.let { it2 ->
                    model.data.title?.let { it3 ->
                        model.data.created_utc?.let { it4 -> formattedDateInHoursAgo(it4) }
                            ?.let { it5 ->
                                return TopContentModel(
                                    it1,
                                    it2,
                                    it5,
                                    it3,
                                    model.data.thumbnail,
                                    if (!model.data.is_video!!) model.data.url else model.data.media?.reddit_video?.fallback_url?.replace(
                                        "?source=fallback",
                                        ""
                                    ),
                                    model.data.num_comments.toString()
                                )
                            }
                    }
                }
            }
        }
        return null
    }

    private fun formattedDateInHoursAgo(date: Double): String {
        val currentTime = System.currentTimeMillis().toDouble()
        val timeAgo =
            (currentTime - date * AppDefaultValues.MILLIS_IN_MINUTE) / AppDefaultValues.MILLIS_IN_HOUR
        return if (timeAgo > 0) "${timeAgo.toInt()} hours ago"
        else "now"
    }
}