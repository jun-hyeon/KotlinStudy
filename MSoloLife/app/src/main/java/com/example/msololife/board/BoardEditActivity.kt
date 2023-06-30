package com.example.msololife.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.msololife.R
import com.example.msololife.databinding.ActivityBoardEditBinding
import com.example.msololife.utils.FBAuth
import com.example.msololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardEditBinding
    private lateinit var key : String
    private lateinit var writeUid : String
    private val TAG = BoardEditActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_edit)

        key = intent.getStringExtra("key").toString()

        getBoardData(key)
        getImageData(key)

        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }

    }

    private fun editBoardData(key : String){
        FBRef.boardRef
            .child(key)
            .setValue(BoardModel(binding.titleArea.text.toString(), binding.contentsArea.text.toString(), writeUid, FBAuth.getTime()))
        finish()
    }


    private fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child("${key}.png")

        // ImageView in your Activity
        val imageView = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener {
            if(it.isSuccessful){
                Glide.with(this)
                    .load(it.result)
                    .into(imageView)
            }

        }.addOnFailureListener {
            Log.d(TAG,it.toString())
        }

    }

    private fun getBoardData(key : String){
        FBRef.boardRef.child(key).addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {
                    val item = snapshot.getValue(BoardModel::class.java)!!

                    binding.titleArea.setText(item.title)
                    binding.contentsArea.setText(item.content)
                    writeUid = item.uid


                }catch (e : java.lang.Exception){

                    Log.d(TAG,"삭제 완료")
                }



            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}