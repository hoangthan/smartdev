package com.smartdev.libraries.movie.domain.usecase.getMovie

import com.smartdev.data.core.model.Either
import com.smartdev.data.core.usecase.FlowUseCase
import com.smartdev.libraries.movie.domain.repository.MovieRepository
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieUseCase.GetMovieParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) : FlowUseCase<GetMovieParam, GetMovieError, List<Movie>> {

    companion object {
        private const val MINIMUM_LENGTH = 3
    }

    override fun invoke(param: GetMovieParam): Flow<Either<GetMovieError, List<Movie>>> {
        if (param.keyword.trim().length <= MINIMUM_LENGTH) {
            return flowOf(Either.Left(GetMovieError.InvalidKeyword))
        }

        return movieRepository.getMovie(param.keyword)
    }

    data class GetMovieParam(
        val keyword: String,
    )
}
