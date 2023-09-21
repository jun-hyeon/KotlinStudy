package com.example.test2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.example.test2.databinding.FragmentFirstBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FirstFragment : Fragment() {
    private var _binding : FragmentFirstBinding? = null
    private val binding : FragmentFirstBinding get() = _binding!!

    private val testViewModel: TestViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                testViewModel.count.collectLatest { value ->
                    binding.textView.text = value.toString()
                }
            }
        }


        binding.increaseBtn.setOnClickListener {
            testViewModel.inCrease()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }



    override fun onStart() {
        super.onStart()
        Log.d("onStart", "호출됨!!")
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}