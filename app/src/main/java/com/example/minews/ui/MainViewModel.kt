package com.example.minews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minews.usecases.RedditDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditDataUseCase: RedditDataUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            redditDataUseCase.getData()
        }
    }
}