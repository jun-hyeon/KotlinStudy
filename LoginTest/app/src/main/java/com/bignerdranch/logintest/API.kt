package com.bignerdranch.logintest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface API {
  @POST("joinMember")
  @Headers("Content-Type: application/json")
  fun postUser(@Body userInfo: JoinMember) : Call<String>

  @GET("repetitionCheckId")
  @Headers("Content-Type: application/json")
  fun getRepetitionCheckId(@Query("memberId") memberId : String) : Call<String>

  @POST("loginMember")
  @Headers("Content-Type: application/json")
  fun loginMember(@Body loginRequest: LoginRequest) : Call<LoginInfo>

  @GET("FeedInquiry")
  @Headers("Content-Type: application/json")
  fun getFeed() : Call<List<FeedQueryItem>>


  @Multipart
  @POST("insertFeed")
  fun writeFeed(
    @Part("data") writeFeedData: FeedQueryItem,
    @Part  uploadImage : List<MultipartBody.Part>
  ) :Call<String>


}