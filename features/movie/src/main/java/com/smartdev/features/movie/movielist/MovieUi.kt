package com.smartdev.features.movie.movielist

import com.smartdev.libraries.movie.domain.usecase.getMovie.Movie

data class MovieUi(
    val imdbID: String,
    val poster: String?,
    val title: String,
    val type: String,
    val year: String,
)

fun Movie.toUi(): MovieUi {
    return MovieUi(
        imdbID = imdbID,
        poster = poster,
        title = title,
        type = type,
        year = year,
    )
}
