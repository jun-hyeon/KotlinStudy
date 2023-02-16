package com.bignerdranch.logintest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface API {
  @POST("joinMember")
  @Headers("Content-Type: application/json")
  fun postUser(@Body userInfo: JoinMember) : Call<String>

  @GET("posts")
  fun getInfo()


  companion object{

    private const val baseUrl = "http://220.121.121.168:8080/"


    fun create() : API {
      val gson : Gson = GsonBuilder().setLenient().create()
      return  Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(baseUrl)
        .build()
        .create(API::class.java)

    }
  }
}