package com.example.minews.repositories

import androidx.paging.PagingData
import com.example.minews.network.parseModels.Children
import kotlinx.coroutines.flow.Flow

interface IRedditDataRepository {
    fun getDataFromReddit(): Flow<PagingData<Children>>
}