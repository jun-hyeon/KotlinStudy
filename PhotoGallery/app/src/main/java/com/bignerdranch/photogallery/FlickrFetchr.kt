package com.bignerdranch.photogallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.photogallery.api.FlickrApi
import retrofit2.*
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "FlickrFetchr"

class FlickrFetchr {

    private val flickrApi : FlickrApi

    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    fun fetchPhotos() : LiveData<String>{
        val responseLiveData : MutableLiveData<String> = MutableLiveData()
        val flickrRequest : Call<String> = flickrApi.fetchPhotos()

        flickrRequest.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG,"Response received")
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG,"Failed to Fetch Photos")
            }

        })
        return responseLiveData
    }

}