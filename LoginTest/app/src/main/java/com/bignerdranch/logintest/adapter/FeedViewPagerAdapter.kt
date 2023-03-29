package com.bignerdranch.logintest.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.logintest.Ff
import com.bignerdranch.logintest.GlideApp
import com.bignerdranch.logintest.R



class FeedViewPagerAdapter(private var images : List<Ff>) : RecyclerView.Adapter<FeedViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var imageUrl = ""
        private val baseUrl = "http://172.30.1.35:8080/images/"
        private val imageView = itemView.findViewById<ImageView>(R.id.feedViewPagerItem)

        fun bind(ffItem : Ff){
            imageUrl = baseUrl + ffItem.feedFileSaveimg
            GlideApp.with(itemView.context).load(imageUrl).fitCenter().into(imageView)
            Log.d("ViewPagerAdapter", imageUrl)
        }
    }

}