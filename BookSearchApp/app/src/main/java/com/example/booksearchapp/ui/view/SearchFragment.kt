package com.example.booksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.databinding.FragmentSearchBinding
import com.example.booksearchapp.ui.adapter.BookSearchAdapter
import com.example.booksearchapp.ui.adapter.BookSearchLoadStateAdapter
import com.example.booksearchapp.ui.adapter.BookSearchPagingAdapter

import com.example.booksearchapp.ui.viewmodel.SearchViewModel
import com.example.booksearchapp.util.Constants.SEARCH_BOOKS_TIME_DELAY
import com.example.booksearchapp.util.collectLatestStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(){
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

//    private lateinit var bookSearchViewModel: BookSearchViewModel
//    private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()
//    private lateinit var bookSearchAdapter : BookSearchAdapter
    private lateinit var bookSearchAdapter : BookSearchPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        setupRecyclerView()
        searchBooks()
        setUpLoadState()

//        bookSearchViewModel.searchResult.observe(viewLifecycleOwner){ response ->
//            val books = response.books
//            bookSearchAdapter.submitList(books)
//        }

        collectLatestStateFlow(searchViewModel.searchPagingResult){
            bookSearchAdapter.submitData(it)
        }
    }

    private fun setupRecyclerView(){
//        bookSearchAdapter = BookSearchAdapter()
        bookSearchAdapter = BookSearchPagingAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookSearchAdapter.withLoadStateFooter(
                footer = BookSearchLoadStateAdapter(bookSearchAdapter::retry)
            )
        }
        bookSearchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToBookFragment(it)
            findNavController().navigate(action)
        }

//        bookSearchAdapter.setItemClickListener(object : BookSearchAdapter.OnItemClickListener{
//            override fun onClick(v: View, position: Int, book: Book) {
//                Toast.makeText(requireContext(),"$book, $position",Toast.LENGTH_LONG).show()
//                val action = SearchFragmentDirections.actionSearchFragmentToBookFragment(book)
//                findNavController().navigate(action)
//            }
//        })



    }

    private fun searchBooks(){
        var startTime = System.currentTimeMillis()
        var endTime: Long

        binding.etSearch.text =
            Editable.Factory.getInstance().newEditable(searchViewModel.query)

        binding.etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if(endTime - startTime >= SEARCH_BOOKS_TIME_DELAY){
                text?.let {
                    val query = it.toString().trim()
                    if(query.isNotEmpty()){
//                        bookSearchViewModel.searchBooks(query)
                        searchViewModel.searchBooksPaging(query)
                        searchViewModel.query = query
                    }
                }
            }
            startTime = endTime
        }
    }

    private fun setUpLoadState(){
        bookSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState  = combinedLoadStates.source
            val isListEmpty = bookSearchAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptylist.isVisible = isListEmpty
            binding.rvSearchResult.isVisible = !isListEmpty

            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading

//            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error                     //LoadState Adapter로 구현
//                    || loadState.append is LoadState.Error
//                    || loadState.prepend is LoadState.Error
//
//            val errorState : LoadState.Error? = loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error
//                ?: loadState.refresh as? LoadState.Error
//            errorState?.let {
//                Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
//            }

//            binding.btnRetry.setOnClickListener {
//                bookSearchAdapter.retry()
//            }
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}