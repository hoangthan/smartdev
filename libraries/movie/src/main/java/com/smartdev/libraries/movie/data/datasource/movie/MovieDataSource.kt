package com.smartdev.libraries.movie.data.datasource.movie

import com.smartdev.data.core.model.Either
import com.smartdev.libraries.movie.data.network.movie.MovieApiService
import com.smartdev.libraries.movie.data.network.movie.dto.toDomain
import com.smartdev.libraries.movie.domain.repository.MovieRepository
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieError
import com.smartdev.libraries.movie.domain.usecase.getMovie.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val movieApiSource: MovieApiService
) : MovieRepository {

    override fun getMovie(keyword: String): Flow<Either<GetMovieError, List<Movie>>> {
        return flow {
            val result = movieApiSource.getMovie(keyword)
            val movies = result.movies.map { it.toDomain() }
            emit(Either.Right(movies))
        }
    }
}
