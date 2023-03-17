package com.smartdev.libraries.movie.data.network.movie

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.smartdev.libraries.movie.data.network.movie.dto.ResultWrapperDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/")
    suspend fun getMovie(
        @Query("s") keyword: String,
        @Query("page") page: Int,
        @Query("type") type: String = "movie",
        @Query("apikey") apiKey: String = "b9bd48a6" //This field should be moved to NDK to keep it secret.
    ): Either<CallError, ResultWrapperDto>
}
