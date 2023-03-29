package com.bignerdranch.logintest

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.bignerdranch.logintest.adapter.WriteFeedAdapter
import com.bignerdranch.logintest.repository.FeedRepository

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FeedWriteActivity : AppCompatActivity() {

    private lateinit var closeImage : ImageView
    private lateinit var content : EditText
    private lateinit var send : TextView
    private lateinit var images : ViewPager2
    private lateinit var album : ImageView
    private lateinit var camera : ImageView
    private lateinit var writeFeedAdapter : WriteFeedAdapter
    private lateinit var getImages : ActivityResultLauncher<Intent>
    private lateinit var file : File

    private var imagePathList = arrayListOf<File>()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_write)

        content = findViewById(R.id.writeFeedEditText)
        send = findViewById(R.id.writeFeedSend)
        album = findViewById(R.id.writeFeedAlbum)
        camera = findViewById(R.id.writeFeedCamera)
        images = findViewById(R.id.writeFeedImageView)


        closeImage = findViewById(R.id.closeImage)


        val memberId = App.prefs.getString("memberId","mo memberId")
        var contentText = content.text?.toString()
        val feedDate = getDate()

        Log.d("WriteFeedPrefs","$memberId, Time: $feedDate")

        closeImage.setOnClickListener {
            finish()
        }

        content.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                contentText = s?.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                contentText = s?.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        album.setOnClickListener {
            val intent = Intent()
            //사진 여러장 불러오기
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            getImages.launch(intent)
        }

        send.setOnClickListener {
          //  window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
          //  writeSend(contentText,getDate(),imagePathList,App.prefs.getString("memberId","no Id"))
              val result = FeedRepository().writeSend(contentText,feedDate,imagePathList,memberId)

                Toast.makeText(this,"now loading...", Toast.LENGTH_SHORT).show()
            result.observe(this, androidx.lifecycle.Observer { result ->
                if (result == "ok"){
                    finish()
                }else{
                    Toast.makeText(this,"Upload Fail!!!", Toast.LENGTH_SHORT).show()
                }
            })
        }



        //인텐트로 앨범에서 사진 가져오기
        getImages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ it ->
            if (it.resultCode == RESULT_OK){
                val clipData = it.data?.clipData
                val clipDataSize = clipData?.itemCount
                val imageList = arrayListOf<Uri>()
                imagePathList = arrayListOf()
                //사진이 1장일때
                if(clipData == null){

                    val selectedImageUri = it?.data?.data!!
                    imageList.add(selectedImageUri)
                    Log.d("data", imageList.toString())

                    file = File(App.realPath.getRealPathFromURI(this,selectedImageUri)!!)
                    imagePathList.add(file)

                        Log.d("FILE","$imagePathList[0]")

                }else{

                    //사진이 1장 이상
                    clipData.let {
                        if (clipDataSize != null) {
                            if (clipDataSize < 5){

                                for(i in 0 until clipDataSize!!){
                                    val selectedImageUri = clipData.getItemAt(i)

                                    imageList.add(selectedImageUri.uri)
                                    Log.d("clipData",imageList[i].toString())
                                    file = File(App.realPath.getRealPathFromURI(this,selectedImageUri.uri)!!)
                                    imagePathList.add(file)
                                    Log.d("clipFile","${imagePathList[i]}")
                                }
                            }else{
                                Toast.makeText(this,"5장 이하로 선택해주세요!!",Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
                getImageList(imageList) //뷰페이저로 사진 보여주기
            }
        }
    }

    //뷰페이저 어댑터
    private fun getImageList(imageList: ArrayList<Uri>){
        images = findViewById(R.id.writeFeedImageView)
        writeFeedAdapter = WriteFeedAdapter(imageList)
        images.offscreenPageLimit = 1
        images.adapter = writeFeedAdapter
    }

    //날짜 가져오기
    private fun getDate(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return simpleDateFormat.format(date).toString()
    }

}