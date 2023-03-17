package com.smartdev.features.movie

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.smartdev.features.core.base.viewmodels.BaseViewModel
import com.smartdev.features.movie.MovieListViewModel.MovieListViewEvent
import com.smartdev.features.movie.movielist.MoviePagingSource
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MovieListViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : BaseViewModel<MovieListViewEvent>() {

    //To make to UI become contentful, load the init content by Marvel keyword temporary
    private val _keywordState = MutableStateFlow("Marvel")

    private val pageConfig = PagingConfig(pageSize = 10)

    val pagingData = _keywordState.debounce(TIME_GAP_SEARCH).flatMapLatest {
            val pagingSource = MoviePagingSource(getMovieUseCase, it)
            Pager(pageConfig) { pagingSource }.flow
        }.cachedIn(viewModelScope).flowOn(Dispatchers.IO)

    private fun updateKeyword(keyword: String) {
        _keywordState.update { keyword }
    }

    override fun dispatchEvent(event: MovieListViewEvent) {
        when (event) {
            is MovieListViewEvent.KeywordChange -> updateKeyword(event.keyword)
        }
    }

    sealed interface MovieListViewEvent {
        data class KeywordChange(val keyword: String) : MovieListViewEvent
    }

    companion object {
        private const val TIME_GAP_SEARCH = 400L
    }
}
