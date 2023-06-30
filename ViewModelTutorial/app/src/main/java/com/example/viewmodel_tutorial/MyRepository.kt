package com.example.viewmodel_tutorial

import androidx.lifecycle.LiveData

interface MyRepository {
    fun getCounter() : LiveData<Int>
    fun increaseCounter()
}