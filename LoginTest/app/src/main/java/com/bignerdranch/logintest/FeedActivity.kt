package com.bignerdranch.logintest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bignerdranch.logintest.adapter.MainViewPagerAdapter
import com.bignerdranch.logintest.databinding.ActivityFeedBinding
import java.util.*

const val TAG = "FeedActivity"


class FeedActivity : AppCompatActivity() {

    private val PERMISSION_ALBUM = 101
    private val REQUEST_STORAGE = 1000
    private var pressTime : Long = 0

    private val permissions : Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)

    //private val api : API = API.create()



    private lateinit var binding:ActivityFeedBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        binding.mainViewPager.adapter = MainViewPagerAdapter(this)
        binding.mainViewPager.offscreenPageLimit = 1
        binding.mainViewPager.currentItem = 0

//        binding.write.setOnClickListener {
//            //퍼미션 체크
//            val permissionCheck = ContextCompat.checkSelfPermission(this,permissions.toString())
//            requestPermissions(permissions,REQUEST_STORAGE)
//
////            if (permissionCheck == PackageManager.PERMISSION_GRANTED){
////                val intent = Intent(this,FeedWriteActivity::class.java)
////                startActivity(intent)
////            }else
//
//        }

        binding.mainViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 -> binding.bottomNavigation.menu.getItem(0).isChecked = true
                    1 -> binding.bottomNavigation.menu.getItem(2).isChecked = true
                }
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener {
                when(it){
                    binding.bottomNavigation.menu.getItem(0) -> {
                        binding.mainViewPager.currentItem = 0
                        true
                    }
                    binding.bottomNavigation.menu.getItem(1) -> {
                        val intent = Intent(this@FeedActivity,FeedWriteActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    binding.bottomNavigation.menu.getItem(2) -> {
                        binding.mainViewPager.currentItem = 1
                        true
                    }

                    else -> false
                }

            }







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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(System.currentTimeMillis() - pressTime < 2500){
            super.getOnBackPressedDispatcher().onBackPressed()
            return
        }
        Toast.makeText(this,"한번 더 클릭 시 홈으로 이동됩니다.",Toast.LENGTH_SHORT).show()
        pressTime = System.currentTimeMillis()
    }



}