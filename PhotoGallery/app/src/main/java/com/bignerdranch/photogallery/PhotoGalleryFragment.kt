package com.bignerdranch.photogallery

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater

private const val TAG = "PhotoGalleryFragment"


class PhotoGalleryFragment : Fragment() {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewmodel
    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       photoGalleryViewModel = ViewModelProvider(this)[PhotoGalleryViewmodel::class.java]
        val responseHandler = Handler(Looper.myLooper() !!)

       thumbnailDownloader = ThumbnailDownloader(responseHandler){
           photoHolder, bitmap ->
           val drawable = BitmapDrawable(resources, bitmap)
           photoHolder.bindDrawable(drawable)
       }

       lifecycle.addObserver(thumbnailDownloader.fragmentLifecycleObserver)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery,container,false)
        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)

        viewLifecycleOwner.lifecycle.addObserver(
            thumbnailDownloader.viewLifecycleObserver
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner,
            Observer { galleryItems ->
                photoRecyclerView.adapter = PhotoAdapter(galleryItems)
            }
        )
    }

  private class PhotoHolder(itemImageview: ImageView): RecyclerView.ViewHolder(itemImageview){


      val bindDrawable : (Drawable) -> Unit = itemImageview::setImageDrawable
  }

  private inner class PhotoAdapter(private val galleryItems: List<GalleryItem>) :
      RecyclerView.Adapter<PhotoHolder>() {

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
          val view = layoutInflater.inflate(R.layout.list_item_gallery,parent,false)
          return PhotoHolder(view as ImageView)
      }

      override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
          val galleryItem = galleryItems[position]
          val placeholder: Drawable = ContextCompat.getDrawable(
              requireContext(),
              R.drawable.bill_up_close)?: ColorDrawable()
          holder.bindDrawable(placeholder)

          thumbnailDownloader.queueThumbnail(holder, galleryItem.url)

      }

      override fun getItemCount(): Int = galleryItems.size

  }

    override fun onDestroyView() {
        super.onDestroyView()
        viewLifecycleOwner.lifecycle.removeObserver(
            thumbnailDownloader.viewLifecycleObserver
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(thumbnailDownloader.fragmentLifecycleObserver)
    }


    companion object{
        fun newInstance() = PhotoGalleryFragment()
    }


}