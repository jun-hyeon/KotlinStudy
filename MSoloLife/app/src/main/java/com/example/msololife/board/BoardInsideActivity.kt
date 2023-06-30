package com.example.msololife.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.msololife.R
import com.example.msololife.comment.CommentModel
import com.example.msololife.comment.CommentRVAdapter
import com.example.msololife.databinding.ActivityBoardInsideBinding
import com.example.msololife.utils.FBAuth
import com.example.msololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardInsideActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardInsideBinding

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var key : String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentRVAdapter: CommentRVAdapter
    private lateinit var linearLayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_inside)

        //첫번째 방법
//        val title = intent.getStringExtra("title")
//        val content = intent.getStringExtra("content")
//        val time = intent.getStringExtra("time")
//        binding.boardTextArea.text = title
//        binding.boardContentArea.text = content
//        binding.boardTimeArea.text = time

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }


        key = intent.getStringExtra("key").toString()
        Log.d(TAG, key.toString())

        getBoardData(key)
        getImageData(key)

        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }

        val rv = binding.commentRV
        commentRVAdapter = CommentRVAdapter(commentDataList)
        linearLayoutManager = LinearLayoutManager(this)
        rv.adapter = commentRVAdapter
        rv.layoutManager = linearLayoutManager

        getCommentData(key)
    }

    private fun getCommentData(key : String){
        FBRef.commentRef.child(key).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


                for (dataModel in snapshot.children){
                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)
                }
                commentRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun insertComment(key : String){
        // comment
        //      - BoardKey
        //          -CommentKey
        //              -CommentData
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(CommentModel(binding.commentArea.text.toString(), FBAuth.getTime()))

        Toast.makeText(this,"댓글 입력 완료", Toast.LENGTH_LONG).show()
        binding.commentArea.setText("")

    }

    private fun showDialog(){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()

        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            //수정
            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key",key)
            startActivity(intent)
        }

        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            //삭제
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this,"삭제 완료",Toast.LENGTH_LONG).show()
            finish()
        }

    }


    private fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child("${key}.png")

        // ImageView in your Activity
        val imageView = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener {
            if(it.isSuccessful){
                Glide.with(this)
                    .load(it.result)
                    .into(imageView)
            }else{
                binding.getImageArea.isVisible = false
            }

        }.addOnFailureListener {
            Log.d(TAG,it.toString())
        }

    }

    private fun getBoardData(key : String){
        FBRef.boardRef.child(key).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                try {
                    val item = snapshot.getValue(BoardModel::class.java)!!

                    binding.boardTextArea.text = item.title
                    binding.boardContentArea.text = item.content
                    binding.boardTimeArea.text = item.time

                    val myUid = FBAuth.getUid()
                    val writeUid = item.uid
                    if (myUid == writeUid){
                        Toast.makeText(baseContext,"내가 글쓴이",Toast.LENGTH_LONG).show()
                        binding.boardSettingIcon.isVisible = true
                    }else{
                        Toast.makeText(baseContext,"내가 글쓴이 아님",Toast.LENGTH_LONG).show()
                    }



                }catch (e : java.lang.Exception){

                    Log.d(TAG,"삭제 완료")
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}