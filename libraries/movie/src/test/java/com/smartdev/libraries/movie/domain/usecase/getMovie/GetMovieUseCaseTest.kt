package com.smartdev.libraries.movie.domain.usecase.getMovie

import arrow.core.Either
import com.smartdev.libraries.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetMovieUseCaseTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var getMovieUseCase: GetMovieUseCase

    @Before
    fun setUp() {
        getMovieUseCase = GetMovieUseCase(movieRepository)
    }

    @Test
    fun `case keyword too short`() {
        runBlocking {
            whenever(movieRepository.getMovie("Movie", 1)).thenReturn(flowOf())
            val actualResult = getMovieUseCase(GetMovieUseCase.GetMovieParam("M")).first()
            val expectedResult = Either.Left(GetMovieError.KeywordTooShortError)
            assertTrue(actualResult == expectedResult)
        }
    }

    @Test
    fun `case keyword enough`() {
        runBlocking {
            whenever(movieRepository.getMovie("Mov", 1)).thenReturn(flowOf(Either.Right(listOf())))
            val actualResult = getMovieUseCase(GetMovieUseCase.GetMovieParam("Mov", 1)).first()
            val expectedResult = Either.Right<List<Movie>>(listOf())
            assertTrue(actualResult == expectedResult)
        }
    }
}