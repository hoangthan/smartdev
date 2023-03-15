package com.smartdev.libraries.movie.data.network.movie.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ResultWrapperDto(
    @Json(name = "Response")
    val response: String,

    @Json(name = "Search")
    val movies: List<MovieDto>,

    @Json(name = "totalResults")
    val totalResults: String,
)
