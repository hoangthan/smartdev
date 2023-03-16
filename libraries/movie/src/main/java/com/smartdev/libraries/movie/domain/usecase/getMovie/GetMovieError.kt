package com.smartdev.libraries.movie.domain.usecase.getMovie

sealed class GetMovieError : Throwable() {
    object InvalidKeyword : GetMovieError()
    object MovieNotFound : GetMovieError()
}
