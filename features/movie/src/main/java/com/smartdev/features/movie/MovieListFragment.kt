package com.smartdev.features.movie

import android.view.LayoutInflater
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.smartdev.features.core.base.fragments.BaseFragment
import com.smartdev.features.core.base.utils.observeFlow
import com.smartdev.features.movie.MovieListViewModel.MovieListViewEvent
import com.smartdev.features.movie.databinding.FragmentMovieListBinding
import com.smartdev.features.movie.movielist.MovieListAdapter
import com.smartdev.features.movie.movielist.MovieUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {

    private val movieListAdapter = MovieListAdapter()
    private val viewModel: MovieListViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater): FragmentMovieListBinding {
        return FragmentMovieListBinding.inflate(inflater)
    }

    override fun initView() {
        binding.rcvMovie.apply {
            adapter = movieListAdapter
            setHasFixedSize(true)
        }
    }

    override fun initViewListener() {
        binding.editText.doOnTextChanged { text, _, _, _ ->
            onSearchKeywordChanged(text.toString())
        }
    }

    override fun initObserver() {
        observeFlow(viewModel.pagingData, ::onDataLoaded)
    }

    private fun onDataLoaded(pagingData: PagingData<MovieUi>) {
        movieListAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
    }

    private fun onSearchKeywordChanged(keyword: String) {
        viewModel.dispatchEvent(MovieListViewEvent.KeywordChange(keyword))
    }
}
