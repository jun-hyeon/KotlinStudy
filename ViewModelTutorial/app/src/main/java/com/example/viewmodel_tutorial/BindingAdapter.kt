package com.example.viewmodel_tutorial

import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("app:progressScaled")
fun setProgress(progressBar : ProgressBar, counter: Int){
    progressBar.progress = counter
}