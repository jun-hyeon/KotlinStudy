package com.example.flowbasics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while(currentValue > 0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow(){
        val flow1 = flow{
            emit(1)
            delay(500L)
            emit(2)
        }

        viewModelScope.launch {
            // collect의 경우 작업이 있을 경우 순차적으로 진행함 collectLatest의 경우 마지막 작업만 진행함
            // operator .map .count .filter .reduce .fold

        }

    }

}