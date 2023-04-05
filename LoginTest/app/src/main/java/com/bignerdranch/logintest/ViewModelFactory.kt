package com.bignerdranch.logintest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.bignerdranch.logintest.repository.FeedRepository

class ViewModelFactory(private val repository : FeedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FeedViewModel::class.java)){

        return FeedViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("unknown ViewModel class")
    }
    }
