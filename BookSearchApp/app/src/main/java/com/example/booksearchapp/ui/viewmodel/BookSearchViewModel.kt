package com.example.booksearchapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.data.model.SearchResponse
import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.woker.CacheDeleteWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//@HiltViewModel
//class BookSearchViewModel @Inject constructor(
//    private val bookSearchRepository: BookSearchRepository,
//    private val savedStateHandle: SavedStateHandle,
//    private val workManager : WorkManager,
//) : ViewModel() {
//
//    //Api
//    private val _searchResult = MutableLiveData<SearchResponse>()
//    val searchResult : LiveData<SearchResponse> get() = _searchResult
//
//    fun searchBooks(query: String) = viewModelScope.launch(Dispatchers.IO){
//        val response = bookSearchRepository.searchBooks(query, getSortMode(),1,15)
//        if(response.isSuccessful){
//            response.body()?.let { body ->
//                _searchResult.postValue(body)
//            }
//        }
//    }
//
//    //Room
//    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.insertBooks(book)
//    }
//
//    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.deleteBooks(book)
//    }
//
//        // val favoriteBooks: Flow<List<Book>> = bookSearchRepository.getFavoriteBooks()
//
//    val favoriteBooks : StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()
//    )
//
//    var query = String()
//        set(value){
//            field = value
//            savedStateHandle.set(SAVE_STATE_KEY, value)
//        }
//
//    init {
//        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
//    }
//
//    companion object{
//        private const val SAVE_STATE_KEY = "query"
//        private val WORKER_KEY = "cache_worker"
//    }
//
//    //DataStore
//    fun saveSortMode(value : String ) = viewModelScope.launch(Dispatchers.IO) {
//        bookSearchRepository.saveSortMode(value)
//    }
//
//    suspend fun getSortMode() = withContext(Dispatchers.IO){
//        bookSearchRepository.getSortMode().first()
//    }
//
//    fun saveCacheDeleteMode(value: Boolean) = viewModelScope.launch {
//        bookSearchRepository.saveCacheDeleteMode(value)
//    }
//
//    suspend fun getCacheDeleteMode() = withContext(Dispatchers.IO){
//        bookSearchRepository.getCacheDeleteMode().first()
//    }
//
//    //Paging
//    val favoritePagingBooks : StateFlow<PagingData<Book>> =
//        bookSearchRepository.getFavoritePagingBooks()
//            .cachedIn(viewModelScope)
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
//
//    private val _searchPagingResult = MutableStateFlow<PagingData<Book>>(PagingData.empty())
//    val searchPagingResult: StateFlow<PagingData<Book>> = _searchPagingResult.asStateFlow()
//
//    fun searchBooksPaging(query: String){
//        viewModelScope.launch {
//            bookSearchRepository.searchBooksPaging(query, getSortMode())
//                .cachedIn(viewModelScope)
//                .collect{
//                    _searchPagingResult.value = it
//                }
//        }
//    }
//
//    //WorkManager
//    fun setWork(){
//        val constraints = Constraints.Builder()
//            .setRequiresCharging(true)
//            .setRequiresBatteryNotLow(true)
//            .build()
//
//        val workRequest = PeriodicWorkRequestBuilder<CacheDeleteWorker>(15, TimeUnit.MINUTES)
//            .setConstraints(constraints)
//            .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            WORKER_KEY, ExistingPeriodicWorkPolicy.UPDATE, workRequest)
//    }
//
//    fun deleteWork() = workManager.cancelUniqueWork(WORKER_KEY)
//
//    fun getWorkStatus() : LiveData<MutableList<WorkInfo>> =
//        workManager.getWorkInfosForUniqueWorkLiveData(WORKER_KEY)
//}