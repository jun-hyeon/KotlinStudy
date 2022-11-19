package com.bignerdranch.photogallery.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=f8f574fea9057e1ccb28913bd76f95ce" +
                "&format=json" +
                "&nojsoncallback=1" +
                "extras=url_s"
    )
    fun fetchPhotos() : Call<String>

}