package com.bignerdranch.photogallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app) {


    val galleryItemLiveData : LiveData<List<GalleryItem>>

    private val flickrFetchr = FlickrFetchr()
    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String
    get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryReferences.QueryReference.getStoredQuery(app)

        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm){
            searchTerm -> if(searchTerm.isBlank()){
                flickrFetchr.fetchPhotos()
        }else{
            flickrFetchr.searchPhotos(searchTerm)
        }
        }
    }

    fun fetchPhotos(query : String = ""){
        QueryReferences.QueryReference.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}