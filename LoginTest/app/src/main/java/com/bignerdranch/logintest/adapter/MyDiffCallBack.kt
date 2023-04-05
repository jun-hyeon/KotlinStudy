package com.bignerdranch.logintest.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.logintest.FeedQueryItem

class MyDiffCallBack : DiffUtil.ItemCallback<FeedQueryItem>() {
    override fun areItemsTheSame(oldItem: FeedQueryItem, newItem: FeedQueryItem): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: FeedQueryItem, newItem: FeedQueryItem): Boolean {
        return oldItem == newItem
    }
}