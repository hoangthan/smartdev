package com.smartdev.features.movie

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.smartdev.features.core.base.viewmodels.BaseViewModel
import com.smartdev.features.movie.MovieListViewModel.MovieListViewEvent
import com.smartdev.features.movie.movielist.MoviePagingSource
import com.smartdev.features.movie.movielist.MoviePagingSource.Companion.RESULT_PER_PAGE
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MovieListViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : BaseViewModel<MovieListViewEvent>() {

    private val pageConfig = PagingConfig(pageSize = RESULT_PER_PAGE)
    private val _keywordState = MutableStateFlow<String?>(null)

    val pagingData = _keywordState
        .debounce(TIME_GAP_SEARCH)
        .filterNotNull()
        .flatMapLatest {
            val pagingSource = MoviePagingSource(getMovieUseCase, it)
            Pager(pageConfig) { pagingSource }.flow
        }
        .cachedIn(viewModelScope)
        .flowOn(Dispatchers.IO)

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
        private const val TIME_GAP_SEARCH = 500L
    }
}
