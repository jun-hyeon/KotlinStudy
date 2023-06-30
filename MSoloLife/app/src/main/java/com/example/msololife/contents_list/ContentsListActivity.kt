package com.example.msololife.contents_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.msololife.R
import com.example.msololife.utils.FBAuth
import com.example.msololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentsListActivity : AppCompatActivity() {

    private lateinit var myRef : DatabaseReference
    private lateinit var rvAdapter : ContentRVAdapter
    val bookmarkIdList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)

        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()

        rvAdapter = ContentRVAdapter(items, itemKeyList, bookmarkIdList)

        val database = Firebase.database

        val category = intent.getStringExtra("category")




        if(category == "category1"){

            myRef = database.getReference("contents")

        }else if(category == "category2"){
             myRef = database.getReference("contents2")
        }



        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(dataModel in dataSnapshot.children){

                    val item = dataModel.getValue(ContentModel::class.java)
                    itemKeyList.add(dataModel.key.toString())

                    items.add(item!!)
                }
                rvAdapter.notifyDataSetChanged()
                Log.d("ContentsListActivity",items.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })



        val rv : RecyclerView = findViewById(R.id.rv)






        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(this,2)

        getBookmark()

// recyclerview item click
//        rvAdapter.itemClick = object : ContentRVAdapter.ItemClick{
//            override fun onClick(view: View, position: Int) {
//                val intent = Intent(this@ContentsListActivity, ContentShowActivity::class.java)
//                intent.putExtra("url",items[position].webUrl)
//                startActivity(intent)
//            }
//
//        }

    }

    private  fun getBookmark(){
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                bookmarkIdList.clear()

                for(dataModel in dataSnapshot.children){
                    bookmarkIdList.add(dataModel.key.toString())
                }
                    Log.d("ContentListActivity",bookmarkIdList.toString())
                    rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })
    }
}