package com.example.test.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.RecyclerviewItemBinding

class MyViewHolder(private val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(count : Int){
        binding.rvItem.text = count.toString()
    }
}