package com.bignerdranch.logintest.adapter

import android.content.Context
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bignerdranch.logintest.FeedQueryItem
import com.bignerdranch.logintest.Ff
import com.bignerdranch.logintest.R

class FeedAdapter() : ListAdapter<FeedQueryItem,FeedAdapter.FeedViewHolder>(MyDiffCallBack())  {

    interface ItemClickListener{
        fun onClick(view: View,position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feedQueryItem = getItem(position)
         holder.bind(feedQueryItem)


    }





     class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         private var ffItem = listOf<Ff>()
         private val feedProfileImg : ImageView = itemView.findViewById(R.id.feedProfileImg)
         private val feedNickName: TextView = itemView.findViewById(R.id.feedNickName)
         private val feedViewPager : ViewPager2 = itemView.findViewById(R.id.feedViewPager)
         private val feedContent: TextView = itemView.findViewById(R.id.feedContent)
         private val feedMore : ImageView  = itemView.findViewById(R.id.feedMore)


         fun bind(feedQueryItem: FeedQueryItem){
             feedNickName.text = feedQueryItem.memberId
             feedContent.text = feedQueryItem.feedContent
             ffItem = feedQueryItem.ffList!!


             feedViewPager.offscreenPageLimit = 1
             feedViewPager.adapter = FeedViewPagerAdapter(ffItem)

             Log.d("RecyclerViewAdapter", ffItem.toString())


         }

     }

}