package com.bignerdranch.logintest.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.logintest.GlideApp
import com.bignerdranch.logintest.R

class WriteFeedAdapter(private val imageList : ArrayList<Uri>,val context: Context) : RecyclerView.Adapter<WriteFeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.write_feed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var imageView : ImageView = itemView.findViewById(R.id.writeFeedItemImage)

        fun bind(mContext: Context, uri: Uri){

            GlideApp.with(mContext).load(uri).fitCenter().into(imageView)
        }
    }
}
