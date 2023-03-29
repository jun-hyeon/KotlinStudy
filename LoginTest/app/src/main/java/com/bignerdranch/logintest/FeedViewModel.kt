package com.bignerdranch.logintest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.logintest.repository.FeedRepository
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val repository = FeedRepository()

    private var _feedLiveData : MutableLiveData<List<FeedQueryItem>>  = MutableLiveData()
    val  feedLiveData : LiveData<List<FeedQueryItem>>
    get() = _feedLiveData



    fun getFeed() = viewModelScope.launch {
        _feedLiveData = repository.getFeed()
    }

}