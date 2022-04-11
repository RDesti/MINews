package com.example.minews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minews.entity.TopContentModel
import com.example.minews.usecases.RedditDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditDataUseCase: RedditDataUseCase
) : ViewModel() {

    private val _listTopContents = MutableLiveData<List<TopContentModel>?>()
    val listTopContents: LiveData<List<TopContentModel>?>
        get() = _listTopContents

    init {
        viewModelScope.launch {
            redditDataUseCase.getData()
        }
    }

    private fun getListTopContents() {
        //todo
        _listTopContents.value = listOf()
    }
}