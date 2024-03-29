package com.example.booksearchapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.booksearchapp.data.repository.BookSearchRepository
import com.example.booksearchapp.woker.CacheDeleteWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
    private val workManager: WorkManager
)  : ViewModel() {

    //DataStore
    fun saveSortMode(value : String ) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.saveSortMode(value)
    }

    suspend fun getSortMode() = withContext(Dispatchers.IO){
        bookSearchRepository.getSortMode().first()
    }

    fun saveCacheDeleteMode(value: Boolean) = viewModelScope.launch {
        bookSearchRepository.saveCacheDeleteMode(value)
    }

    suspend fun getCacheDeleteMode() = withContext(Dispatchers.IO){
        bookSearchRepository.getCacheDeleteMode().first()
    }

    //WorkManager
    fun setWork(){
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<CacheDeleteWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORKER_KEY, ExistingPeriodicWorkPolicy.UPDATE, workRequest)
    }

    fun deleteWork() = workManager.cancelUniqueWork(WORKER_KEY)

    fun getWorkStatus() : LiveData<MutableList<WorkInfo>> =
        workManager.getWorkInfosForUniqueWorkLiveData(WORKER_KEY)

    companion object{
        private const val SAVE_STATE_KEY = "query"
        private val WORKER_KEY = "cache_worker"
    }


}