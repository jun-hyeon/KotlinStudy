package com.bignerdranch.logintest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.logintest.adapter.FeedAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

const val feedActivity = "FeedActivity"


class FeedActivity : AppCompatActivity() {

    private val PERMISSION_ALBUM = 101
    private val REQUEST_STORAGE = 1000

    private val permissions : Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)

    private val api : API = API.create()
    private lateinit var recyclerView : RecyclerView
    private lateinit var feedAdapter : FeedAdapter
    private lateinit var newFeed : TextView
    private lateinit var layoutManager: LinearLayoutManager


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        newFeed = findViewById(R.id.newFeed)
        newFeed.setOnClickListener {
            //퍼미션 체크
            val permissionCheck = ContextCompat.checkSelfPermission(this,permissions.toString())
            requestPermissions(permissions,REQUEST_STORAGE)

//            if (permissionCheck == PackageManager.PERMISSION_GRANTED){
//                val intent = Intent(this,FeedWriteActivity::class.java)
//                startActivity(intent)
//            }else

        }


        getFeed()



    }

    override fun onRestart() {
        super.onRestart()
        getFeed()

    }

    private fun getFeed(){
        //피드 조회
        api.getFeed().enqueue(object : Callback<List<FeedQueryItem>>{
            override fun onResponse(
                call: Call<List<FeedQueryItem>>,
                response: Response<List<FeedQueryItem>>
            ) {
                Log.d(feedActivity, "연결성공 ${response.code()}")

                if (response.isSuccessful){
                    Log.d(feedActivity,"${response.body()}")

                    getList(response.body()!!)


                    Log.d("size","${response.body()}")

                }else{
                    Log.d(feedActivity, "불완전 ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<FeedQueryItem>>, t: Throwable) {
                Log.d(feedActivity, "연결실패 ${t.message}")
            }

        })
    }

    //리사이클러뷰 어댑터연결
   private fun getList(feedItems : List<FeedQueryItem>){

        recyclerView = findViewById(R.id.feedRecyclerView)
        feedAdapter = FeedAdapter((feedItems),this)
        recyclerView.adapter = feedAdapter
        layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager


    }

    //퍼미션 확인 메서드
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_STORAGE ->{
                if (grantResults.isNotEmpty()){
                    var isPermitted = true
                    val deniedPermission = arrayListOf<String>()
                    for((index, value )in grantResults.withIndex()){
                        if(value == PackageManager.PERMISSION_DENIED){
                            isPermitted = false
                            Log.d("PERMISSION", "${grantResults[index]}")
                            deniedPermission.add(grantResults[index].toString())
                        }
                    }
                    if (isPermitted){
                        val intent = Intent(this,FeedWriteActivity::class.java)
                        startActivity(intent)
                    }else{
                        showDialog()
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDialog(){
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의"){_,_ ->
                requestPermissions(
                    permissions,
                    PERMISSION_ALBUM
                )
            }
            .setNegativeButton("취소"){_, _ ->
                this.finish()
            }
            .create()
            .show()
    }

}