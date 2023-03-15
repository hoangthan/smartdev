package com.smartdev.libraries.movie.data.network.movie.dto

import com.smartdev.libraries.movie.domain.usecase.getMovie.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "imdbID")
    val imdbID: String,

    @Json(name = "Poster")
    val poster: String,

    @Json(name = "Title")
    val title: String,

    @Json(name = "Type")
    val type: String,

    @Json(name = "Year")
    val year: String
)

fun MovieDto.toDomain(): Movie {
    return Movie(
        imdbID = imdbID,
        poster = poster,
        title = title,
        type = type,
        year = year
    )
}
