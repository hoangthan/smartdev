package com.smartdev.libraries.movie.domain.usecase.getMovie

sealed interface GetMovieError {
    object InvalidKeyword : GetMovieError
    object MovieNotFound : GetMovieError
}
