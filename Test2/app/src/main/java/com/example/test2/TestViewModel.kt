package com.example.test2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TestViewModel() : ViewModel() {
    var count = MutableStateFlow<Int>(0)

    fun inCrease(){
        count.value = count.value.plus(1)
    }

    fun deCrease(){
        count.value = count.value.minus(1)
    }
}