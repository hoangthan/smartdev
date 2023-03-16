package com.smartdev.libraries.movie.domain.repository

import arrow.core.Either
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieError
import com.smartdev.libraries.movie.domain.usecase.getMovie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovie(keyword: String, page: Int): Flow<Either<GetMovieError, List<Movie>>>
}
