package com.smartdev.libraries.movie.domain.repository

import com.smartdev.data.core.model.Either
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieError
import com.smartdev.libraries.movie.domain.usecase.getMovie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovie(keyword: String, page: Int): Flow<Either<GetMovieError, List<Movie>>>
}
