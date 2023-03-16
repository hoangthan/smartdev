package com.smartdev.libraries.movie.domain.usecase.getMovie

sealed class GetMovieError(open val msg: String) : Throwable(msg) {
    object KeywordTooShort : GetMovieError("Keyword must be at least 3 characters")
    data class InvalidParam(override val msg: String) : GetMovieError(msg)
}
