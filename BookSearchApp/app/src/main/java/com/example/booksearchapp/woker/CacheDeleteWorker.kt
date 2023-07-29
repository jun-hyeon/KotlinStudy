package com.example.booksearchapp.woker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CacheDeleteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val cacheDeleteResult: String
) : Worker(context, workerParameters)  {
    override fun doWork(): Result {
        return try{
//            Log.d("WorkManager", "Cache has successfully deleted")
            Log.d("WorkManager", cacheDeleteResult)
            Result.success()
        } catch (exception: Exception){
            exception.printStackTrace()
            Result.failure()
        }
    }

}