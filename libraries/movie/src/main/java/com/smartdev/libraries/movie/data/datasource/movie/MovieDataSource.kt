package com.smartdev.libraries.movie.data.datasource.movie

import arrow.core.Either
import com.smartdev.libraries.movie.data.network.movie.MovieApiService
import com.smartdev.libraries.movie.data.network.movie.dto.MovieDto
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

    override fun getMovie(keyword: String, page: Int): Flow<Either<GetMovieError, List<Movie>>> {
        return flow {
            val response = movieApiSource.getMovie(keyword = keyword, page = page)

            val result = response
                .map { result ->
                    result.movies
                        .filterNot(::shouldTakeMovie)
                        .map { it.toDomain() }
                }
                .mapLeft {
                    GetMovieError.UnExpected(it.toString())
                }

            emit(result)
        }
    }

    private fun shouldTakeMovie(movie: MovieDto): Boolean {
        //TODO: To make the UI more beautiful, we gonna ignore the item has no poster value temporary
        return movie.poster.isNullOrBlank() || movie.poster == "N/A"
    }
}
