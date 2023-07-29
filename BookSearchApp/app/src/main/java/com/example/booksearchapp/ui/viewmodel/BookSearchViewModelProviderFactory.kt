package com.example.booksearchapp.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import androidx.work.WorkManager
import com.example.booksearchapp.data.repository.BookSearchRepository
import java.lang.IllegalArgumentException
//@Suppress("UNCHECKED_CAST")
//class BookSearchViewModelProviderFactory (private val bookSearchRepository: BookSearchRepository) : ViewModelProvider.Factory{
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(BookSearchViewModel::class.java)){
//            return BookSearchViewModel(bookSearchRepository) as T
//        }
//        throw IllegalArgumentException("ViewModel class not found")
//    }
//}


//class BookSearchViewModelProviderFactory(
//    private val booksSearchRepository: BookSearchRepository,
//    owner: SavedStateRegistryOwner,
//    defaultArgs : Bundle? = null,
//    private val workerManager : WorkManager
//) : AbstractSavedStateViewModelFactory(owner, defaultArgs){
//    override fun <T : ViewModel> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T {
//        if(modelClass.isAssignableFrom(BookSearchViewModel::class.java)){
//            return BookSearchViewModel(booksSearchRepository, handle, workerManager) as T
//        }
//        throw IllegalArgumentException("ViewModel class not found")
//    }
//}