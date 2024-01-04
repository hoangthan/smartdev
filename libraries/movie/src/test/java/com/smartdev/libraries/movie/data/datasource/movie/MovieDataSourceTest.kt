package com.smartdev.libraries.movie.data.datasource.movie

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.IOError
import com.smartdev.libraries.movie.data.network.movie.MovieApiService
import com.smartdev.libraries.movie.data.network.movie.dto.MovieDto
import com.smartdev.libraries.movie.data.network.movie.dto.ResultWrapperDto
import com.smartdev.libraries.movie.domain.repository.MovieRepository
import com.smartdev.libraries.movie.domain.usecase.getMovie.GetMovieError
import com.smartdev.libraries.movie.domain.usecase.getMovie.Movie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class MovieDataSourceTest {

    @Mock
    private lateinit var movieApiService: MovieApiService

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = MovieDataSource(movieApiService)
    }

    private val mockResponse = ResultWrapperDto(
        movies = listOf(
            MovieDto(imdbID = "1", poster = "https://google.com", title = "Movie1", type = "movie", year = "2023"),
            MovieDto(imdbID = "2", poster = "https://google.com", title = "Movie2", type = "movie", year = "2023"),
            MovieDto(imdbID = "3", poster = null, title = "Movie1", type = "Movie3", year = "2023"),
            MovieDto(imdbID = "4", poster = "https://google.com", title = "Movie4", type = "movie", year = "2023"),
            MovieDto(imdbID = "5", poster = "N/A", title = "Movie1", type = "Movie5", year = "2023"),
            MovieDto(imdbID = "6", poster = "https://google.com", title = "Movie6", type = "movie", year = "2023"),
        ),
        response = "True",
        totalResults = "6",
    )

    @Test
    fun `case filter movie has the poster null or invalid`() {
        runBlocking {
            whenever(movieApiService.getMovie("Movie", 1)).thenReturn(Either.Right(mockResponse))
            val actual = movieRepository.getMovie("Movie", 1).first()

            val expectedList = listOf(
                Movie(imdbID = "1", poster = "https://google.com", title = "Movie1", type = "movie", year = "2023"),
                Movie(imdbID = "2", poster = "https://google.com", title = "Movie2", type = "movie", year = "2023"),
                Movie(imdbID = "4", poster = "https://google.com", title = "Movie4", type = "movie", year = "2023"),
                Movie(imdbID = "6", poster = "https://google.com", title = "Movie6", type = "movie", year = "2023"),
            )

            val expectedResult = Either.Right(expectedList)
            assertTrue(actual == expectedResult)
        }
    }

    @Test
    fun `case api call getting error`() {
        runBlocking {
            val page = 1
            val movieName = "Movie"
            val ioError = IOError(IOException("NetworkError"))

            whenever(movieApiService.getMovie(movieName, page)).thenReturn(Either.Left(ioError))

            val actualResult = movieRepository.getMovie(movieName, page).first()
            val expectedResult = Either.Left(GetMovieError.UnExpectedError(ioError.toString()))

            assertTrue(actualResult == expectedResult)
        }
    }
}