package com.smartdev.libraries.movie.domain.usecase.getMovie

import com.smartdev.data.core.model.AppError

sealed class GetMovieError(override val msg: String) : AppError.FeatureError(msg = msg) {
    object KeywordTooShort : GetMovieError("Keyword must be at least 3 characters")
    data class UnExpected(override val msg: String) : GetMovieError(msg)
}
