package com.bignerdranch.logintest.repository

import com.bignerdranch.logintest.API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {




        private val gson : Gson = GsonBuilder().setLenient().create()
        private val client: Retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://172.30.1.35:8080/")
            .build()


        fun getInstance() : Retrofit {
            return client
        }

}