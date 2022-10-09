package com.bignerdranch.criminalintent

import androidx.recyclerview.widget.DiffUtil

class CrimeDiffCallback : DiffUtil.ItemCallback<Crime>() {
    override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
       return oldItem.id == newItem.id
    }

}