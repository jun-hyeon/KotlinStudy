package com.bignerdranch.logintest.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.logintest.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

private const val TAG = "FeedRepository"

class FeedRepository {

    private val api = RetrofitInstance.getInstance().create(API::class.java)
    //피드 조회
   fun getFeed(): MutableLiveData<List<FeedQueryItem>>{
        val responseLiveData : MutableLiveData<List<FeedQueryItem>> = MutableLiveData()

        api.getFeed().enqueue(object : Callback<List<FeedQueryItem>>{
            override fun onResponse(
                call: Call<List<FeedQueryItem>>,
                response: Response<List<FeedQueryItem>>
            ) {
                responseLiveData.value = response.body()
                Log.d(TAG,"Response received ${responseLiveData.value} ")
                Log.d(TAG,"size: ${responseLiveData.value?.size}")

            }

            override fun onFailure(call: Call<List<FeedQueryItem>>, t: Throwable) {
                Log.e(TAG,"Failed to getFeed()",t)
            }

        })
        return responseLiveData
    }
    //아이디 체크
    fun idCheck(memberId: String) : LiveData<String>{
        val result : MutableLiveData<String> = MutableLiveData()
        api.getRepetitionCheckId(memberId).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                result.value = response.body().toString()
                Log.d(TAG,"idCheck Response: ${response.code()} , $result")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "idCheck fail",t)
            }

        })
        return result
    }
    //로그인
    fun loginRequest(loginRequest : LoginRequest) : LiveData<LoginInfo> {
        val loginInfo : MutableLiveData<LoginInfo> = MutableLiveData()

        api.loginMember(loginRequest).enqueue(object : Callback<LoginInfo>{
            override fun onResponse(call: Call<LoginInfo>, response: Response<LoginInfo>) {

                loginInfo.value = response.body()

                Log.d(TAG,response.body().toString())

            }

            override fun onFailure(call: Call<LoginInfo>, t: Throwable) {
                Log.e(TAG, "loginMember fail",t)
            }

        })
        return loginInfo
    }

    //피드 작성
    fun writeSend(content : String?, date : String, imageFileList : ArrayList<File>, memberId : String) : MutableLiveData<String>{
        val result : MutableLiveData<String> = MutableLiveData()

        val files = arrayListOf<MultipartBody.Part>()

        for(i in 0 until imageFileList.size){
            val requestFile = RequestBody.create(MediaType.parse("image/*"),imageFileList[i])
            val filePart = MultipartBody.Part.createFormData("uploadImage",imageFileList[i].name,requestFile)

            files.add(filePart)
        }

        val feedQueryItem = FeedQueryItem(content, date,null,null, memberId)

        api.writeFeed(feedQueryItem,files).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                Log.d("write OK", "${response.body()}, ${response.code()}")
                if (response.body() != null)
                    result.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("write fail", "$t")

            }

        })
       return result
    }
}