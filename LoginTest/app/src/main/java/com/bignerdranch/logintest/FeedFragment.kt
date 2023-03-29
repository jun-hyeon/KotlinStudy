package com.bignerdranch.logintest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.logintest.adapter.FeedAdapter
import com.bignerdranch.logintest.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {


    private lateinit var adapter: FeedAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!


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
        val sharedViewModel = ViewModelProvider(this)[FeedViewModel::class.java]

        sharedViewModel.getFeed()

        sharedViewModel.feedLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d(TAG, "items: $it")
            adapter = FeedAdapter((it))
            binding.feedRecyclerView.adapter = adapter
            linearLayoutManager = LinearLayoutManager(requireActivity())
            linearLayoutManager.reverseLayout = true
            linearLayoutManager.stackFromEnd = true
            binding.feedRecyclerView.layoutManager = linearLayoutManager
            adapter.notifyDataSetChanged()

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}