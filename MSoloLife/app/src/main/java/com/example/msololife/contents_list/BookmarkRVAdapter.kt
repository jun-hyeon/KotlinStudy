package com.example.msololife.contents_list

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.msololife.R
import com.example.msololife.utils.FBAuth
import com.example.msololife.utils.FBRef

class BookmarkRVAdapter(private val items: ArrayList<ContentModel>,
                                val itemKeyList: ArrayList<String>,
                                val bookmarkIdList : MutableList<String>) : RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>(){

//    interface ItemClick{
//        fun onClick(view: View, position: Int)
//    }
//    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)

        Log.d("BookmarkRVAdapter",itemKeyList.toString())
        Log.d("BookmarkRVAdapter",bookmarkIdList.toString())
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: BookmarkRVAdapter.ViewHolder, position: Int) {

//        if(itemClick != null){
//            holder.itemView.setOnClickListener{
//                itemClick?.onClick(it,position)
//            }
//        }

        holder.bind(items[position], itemKeyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){



        private val contentTitle = itemView.findViewById<TextView>(R.id.textArea)
        private val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
        private val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)



        fun bind(item: ContentModel, key: String){

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ContentShowActivity::class.java)
                intent.putExtra("url",item.webUrl)
                itemView.context.startActivity(intent)
            }

            if (bookmarkIdList.contains(key)){
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            }else{
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }




            contentTitle.text = item.title
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(imageViewArea)
        }
    }
}
