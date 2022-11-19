package com.bignerdranch.photogallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.photogallery.api.FlickrApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "FlickFetchr"

class FlickrFetchr {
    private val flickrApi: FlickrApi

    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)

    }

    fun fetchPhotos() : LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val flickrRequest: Call<String> = flickrApi.fetchPhotos()

        flickrRequest.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "response received")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Falied to FlickFetchr")
            }

        })
        return responseLiveData
    }
}