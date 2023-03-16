package com.smartdev.libraries.movie.data.network.movie.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieErrorDto(
    @Json(name = "Response")
    val response: String,

    @Json(name = "Error")
    val error: String,
)
