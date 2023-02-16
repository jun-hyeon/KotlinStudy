package com.bignerdranch.photogallery

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import java.util.concurrent.TimeUnit

private const val TAG = "PhotoGalleryFragment"
private const val POLL_WORK = "POLL_WORK"


class PhotoGalleryFragment : Fragment() {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       photoGalleryViewModel = ViewModelProvider(this)[PhotoGalleryViewModel::class.java]
        val responseHandler = Handler(Looper.myLooper() !!)

       thumbnailDownloader = ThumbnailDownloader(responseHandler){
           photoHolder, bitmap ->
           val drawable = BitmapDrawable(resources, bitmap)
           photoHolder.bindDrawable(drawable)
       }

       lifecycle.addObserver(thumbnailDownloader.fragmentLifecycleObserver)


        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_photo_gallery, menu)

        val searchItem : MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView  = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.apply {
            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG,"QueryTextSubmit: $query")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG,"QueryTextChanged: $query")
                    return false
                }

            })

            setOnSearchClickListener {
                searchView.setQuery(photoGalleryViewModel.searchTerm, false)
            }
        }

        val toggleItem = menu.findItem(R.id.menu_item_toggle_polling)
        val isPolling = QueryReferences.QueryReference.isPolling(requireContext())
        val toggleItemTitle = if(isPolling){
            R.string.stop_polling
        }else{
            R.string.start_polling
        }
        toggleItem.setTitle(toggleItemTitle)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_item_clear ->{
                photoGalleryViewModel.fetchPhotos("")
                true
            }
            R.id.menu_item_toggle_polling -> {
                val isPolling = QueryReferences.QueryReference.isPolling(requireContext())
                if(isPolling){
                    WorkManager.getInstance(requireContext()).cancelUniqueWork(POLL_WORK)
                    QueryReferences.QueryReference.setPolling(requireContext(), false)
                }else{
                    val constraints = Constraints.Builder()
                        .build()
                    val periodRequest = PeriodicWorkRequest
                        .Builder(PollWorker::class.java, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build()
                    WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(POLL_WORK, ExistingPeriodicWorkPolicy.KEEP,periodRequest)
                    QueryReferences.QueryReference.setPolling(requireContext(), true)
                }
                activity?.invalidateOptionsMenu()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

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
              R.drawable.loading)?: ColorDrawable()
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