package com.example.minews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.minews.entity.TopContentModel
import com.example.minews.network.RedditPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditPagingSource: RedditPagingSource

) : ViewModel() {
    private var contentPagingData: Flow<PagingData<TopContentModel>>? = null

    fun getData(): Flow<PagingData<TopContentModel>> {
        if (contentPagingData == null) {
            contentPagingData = Pager(
                config = PagingConfig(
                    pageSize = 5,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { redditPagingSource }
            ).flow.cachedIn(viewModelScope)
        }
        return contentPagingData as Flow<PagingData<TopContentModel>>
    }
}