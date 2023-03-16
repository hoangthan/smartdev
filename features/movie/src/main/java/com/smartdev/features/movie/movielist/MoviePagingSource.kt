package com.smartdev.features.movie.movielist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smartdev.data.core.model.Either
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieUseCase
import kotlinx.coroutines.flow.first

class MoviePagingSource constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val query: String,
) : PagingSource<Int, MovieUi>() {

    override fun getRefreshKey(state: PagingState<Int, MovieUi>): Int? {
        return state.anchorPosition?.let {
            val previous = state.closestPageToPosition(it)?.prevKey?.plus(1)
            val next = state.closestPageToPosition(it)?.nextKey?.minus(1)
            previous ?: next
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieUi> {
        val pageNumber = params.key ?: 1
        val useCaseParam = GetMovieUseCase.GetMovieParam(
            keyword = query,
            page = pageNumber,
        )

        return when (val result = getMovieUseCase(useCaseParam).first()) {
            is Either.Right -> {
                val movies = result.data.map { it.toUi() }
                val prevKey = if (pageNumber == 1) null else pageNumber - 1
                val nextKey = if (movies.isEmpty()) null else pageNumber + 1
                LoadResult.Page(data = movies, prevKey = prevKey, nextKey = nextKey)
            }
            is Either.Left -> LoadResult.Error(result.err)
        }
    }
}
