package com.bignerdranch.logintest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bignerdranch.logintest.App.Companion.prefs
import com.bignerdranch.logintest.adapter.FeedAdapter
import com.bignerdranch.logintest.databinding.FragmentFeedBinding
import com.bignerdranch.logintest.repository.FeedRepository

const val fragment = "Feed Fragment"
class FeedFragment : Fragment() {


    private lateinit var linearLayoutManager: LinearLayoutManager
    private val repository = FeedRepository()
    private  val feedAdapter: FeedAdapter by lazy{
        FeedAdapter()
    }

    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FeedViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(fragment,"onViewCreated")

        binding.feedRecyclerView.apply {
            linearLayoutManager = LinearLayoutManager(requireActivity())
            linearLayoutManager.reverseLayout = true
            linearLayoutManager.stackFromEnd = true
            binding.feedRecyclerView.layoutManager = linearLayoutManager
            adapter = feedAdapter

        }

        viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[FeedViewModel::class.java]

        updateView()


        binding.swipeRefresh.setOnRefreshListener {
           updateView()
            binding.swipeRefresh.isRefreshing = false
        }

        feedAdapter.setItemClickListener(object : FeedAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

                val popupMenu = PopupMenu(requireContext(), binding.feedRecyclerView[position].findViewById(R.id.feedMore))

                popupMenu.inflate(R.menu.recyclerview_menu_options)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.item_delete -> {

                            Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                            true

                        }

                        R.id.item_edit -> {

                            Toast.makeText(context, "수정화면으로 이동합니다..", Toast.LENGTH_SHORT).show()
                            true

                        }
                        else -> {
                            false
                        }
                    }
                }
                popupMenu.show()

            }

        })




    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(fragment, "onViewStateRestored")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d(fragment, "onStart")
        super.onStart()
        updateView()
    }

    override fun onResume() {
        Log.d(fragment, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(fragment, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(fragment, "onStop")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(fragment, "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(fragment, "onDestroyView")
        super.onDestroyView()
    }


    override fun onDetach() {
        Log.d(fragment, "onDetach")
        super.onDetach()
    }




    override fun onDestroy() {
        super.onDestroy()
        Log.d(fragment, "onDestroy")
        _binding = null
    }

    private fun updateView(){
        viewModel.getFeed()
        viewModel.feedLiveData.observe(viewLifecycleOwner) {
            feedAdapter.submitList(it)

            Log.d("listSize","${it.size}")
        }
    }



}