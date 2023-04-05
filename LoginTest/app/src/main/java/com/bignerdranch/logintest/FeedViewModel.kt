package com.bignerdranch.logintest

import android.arch.lifecycle.Transformations
import android.util.Log
import androidx.lifecycle.*

import com.bignerdranch.logintest.repository.FeedRepository
import com.bignerdranch.logintest.repository.RetrofitInstance
import kotlinx.coroutines.launch


class FeedViewModel(private val repository : FeedRepository) : ViewModel() {



    private var _feedLiveData : MutableLiveData<List<FeedQueryItem>>  = MutableLiveData()
    val  feedLiveData : LiveData<List<FeedQueryItem>>
    get() = _feedLiveData

    init {
        getFeed()
    }



      fun getFeed() = viewModelScope.launch() {
        _feedLiveData = repository.getFeed()

        Log.d("ViewModel","Loaded")
    }





}