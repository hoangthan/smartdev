package com.smartdev.libraries.movie.domain.usecase.getMovie

import com.smartdev.data.core.model.AppError

sealed class GetMovieError(override val msg: String? = null) : AppError.FeatureError(msg = msg) {
    object KeywordTooShortError : GetMovieError()
    data class UnExpectedError(override val msg: String) : GetMovieError(msg)
}
