package com.example.booksearchapp.ui.view



import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.booksearchapp.R
import com.example.booksearchapp.databinding.FragmentSettingsBinding

import com.example.booksearchapp.ui.viewmodel.SettingViewModel
import com.example.booksearchapp.util.Sort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(){
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!
//    private  lateinit var bookSearchViewModel : BookSearchViewModel
//    private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val settingsViewModel by viewModels<SettingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        saveSetting()
        loadSetting()
        showWorkStatus()
    }

    private fun saveSetting(){
        binding.rgSort.setOnCheckedChangeListener{_, checkId ->
            val value = when(checkId){
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }
            settingsViewModel.saveSortMode(value)
        }
        binding.swCacheDelete.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveCacheDeleteMode(isChecked)
            if(isChecked){
                settingsViewModel.setWork()
            }else{
                settingsViewModel.deleteWork()
            }
        }


    }

    private fun showWorkStatus() {
        settingsViewModel.getWorkStatus().observe(viewLifecycleOwner){workInfo ->
            Log.d("WorkManager", workInfo.toString())
            if(workInfo.isEmpty()){
                binding.tvWorkStatus.text = "No works"
            }else{
                binding.tvWorkStatus.text = workInfo[0].state.toString()
            }
        }
    }

    private fun loadSetting(){
        lifecycleScope.launch {
            val buttonId = when(settingsViewModel.getSortMode()){
                Sort.ACCURACY.value -> R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }

        //WorkManager
        lifecycleScope.launch {
            val mode = settingsViewModel.getCacheDeleteMode()
            binding.swCacheDelete.isChecked = mode
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}