package com.smartdev.libraries.movie.data.network.movie

import com.smartdev.libraries.movie.data.network.movie.dto.ResultWrapperDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/")
    suspend fun getMovie(
        @Query("s") keyword: String, @Query("page") page: Int = 1
    ): ResultWrapperDto
}
