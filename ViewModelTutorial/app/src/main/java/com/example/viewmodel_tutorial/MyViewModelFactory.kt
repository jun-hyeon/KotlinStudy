package com.example.viewmodel_tutorial


import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
//class MyViewModelFactory (private val counter : Int): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(MyViewModel::class.java)){
//            return MyViewModel(counter) as T
//        }
//        throw IllegalArgumentException ("ViewModel class not found")
//    }
class MyViewModelFactory(
    private val counter : Int,
    owner : SavedStateRegistryOwner,
    defaultArgs : Bundle? = null
    ) : AbstractSavedStateViewModelFactory(owner, defaultArgs){
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if(modelClass.isAssignableFrom(MyViewModel::class.java)){
            return MyViewModel(counter, handle) as T
        }
        throw IllegalArgumentException ("ViewModel class not found")
    }
}