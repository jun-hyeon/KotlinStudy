package com.bignerdranch.photogallery

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.nfc.Tag
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ConcurrentHashMap

private const val TAG = "ThumbnailDownloader"
private const val MESSAGE_DOWNLOAD = 0

class ThumbnailDownloader<in T>
    (private val responseHandler: Handler,
     private val onThumbnailDownloaded: (T, Bitmap) -> Unit) : HandlerThread(TAG){

    private var hasQuit = false

    private lateinit var requestHandler: Handler
    private val requestMap = ConcurrentHashMap<T, String>()
    private val flickrFetchr = FlickrFetchr()

    val fragmentLifecycleObserver : LifecycleEventObserver =
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                Log.i(TAG, "Destroy background thread")
                start()
                looper
            }
            if (event == Lifecycle.Event.ON_DESTROY) {
                Log.i(TAG, "Destroy background thread")
                quit()

            }
        }

    val viewLifecycleObserver: LifecycleEventObserver =
        object : LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
             if(event == Lifecycle.Event.ON_DESTROY){
                 Log.i(TAG,"Clearing all requests from queue")
                 requestHandler.removeMessages(MESSAGE_DOWNLOAD)
                  requestMap.clear()
             }
           }
        }

    @Suppress("UNCHECKED_CAST")
    @SuppressLint("HandlerLeak")
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        requestHandler = object : Handler(Looper.myLooper()!!){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if(msg.what == MESSAGE_DOWNLOAD){
                    val target = msg.obj as T
                    Log.i(TAG,"Got a request for URL: ${requestMap[target]}")
                    handleRequest(target)
                }
            }
        }
    }

    private fun handleRequest(target: T){
        val url = requestMap[target] ?: return
        val bitmap = flickrFetchr.fetchPhoto(url) ?: return

        responseHandler.post(Runnable {
            if(requestMap[target] != url || hasQuit){
                return@Runnable
            }
            requestMap.remove(target)
            onThumbnailDownloaded(target,bitmap)
        })
    }

    override fun quit() : Boolean{
        hasQuit = true
        return super.quit()
    }



    fun queueThumbnail(target: T, url: String){
        Log.i(TAG,"Got a URL: $url")
        requestMap[target] = url
        requestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
            .sendToTarget()
    }

     }
