package com.smartdev.features.movie

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import com.smartdev.features.core.base.fragments.BaseFragment
import com.smartdev.features.core.base.utils.boundWithViewLifeCycle
import com.smartdev.features.core.base.utils.observeFlow
import com.smartdev.features.core.base.utils.showToast
import com.smartdev.features.movie.MovieListViewModel.MovieListViewEvent
import com.smartdev.features.movie.databinding.FragmentMovieListBinding
import com.smartdev.features.movie.movielist.MovieListAdapter
import com.smartdev.features.movie.movielist.MovieUi
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {

    private val viewModel: MovieListViewModel by viewModels()
    private val movieListAdapter by boundWithViewLifeCycle { MovieListAdapter() }

    override fun getViewBinding(inflater: LayoutInflater): FragmentMovieListBinding {
        return FragmentMovieListBinding.inflate(inflater)
    }

    override fun initView() {
        binding.rcvMovie.apply {
            adapter = movieListAdapter
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    override fun initViewListener() {
        binding.edtMovieName.doOnTextChanged { text, _, _, _ ->
            onSearchKeywordChanged(text.toString())
        }
    }

    override fun initObserver() {
        observeFlow(viewModel.pagingData, ::onDataLoaded)
        movieListAdapter.addLoadStateListener(::onLoadStateChanged)
    }

    private fun onLoadStateChanged(state: CombinedLoadStates) {
        val refresh = state.refresh
        binding.loadingView.isVisible = refresh is LoadState.Loading
        if (refresh is LoadState.Error) showError(refresh.error as? GetMovieError)
    }

    private fun showError(getMovieError: GetMovieError?) {
        val stringRes = when (getMovieError) {
            is GetMovieError.KeywordTooShortError -> R.string.key_word_too_short
            is GetMovieError.UnExpectedError -> R.string.network_error
            null -> R.string.unknown_error
        }

        showToast(getString(stringRes))
    }

    private fun onDataLoaded(pagingData: PagingData<MovieUi>) {
        movieListAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
    }

    private fun onSearchKeywordChanged(keyword: String) {
        viewModel.dispatchEvent(MovieListViewEvent.KeywordChange(keyword))
    }
}
