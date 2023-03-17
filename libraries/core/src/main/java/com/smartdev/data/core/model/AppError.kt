package com.smartdev.data.core.model

sealed class AppError(open val msg: String? = null, open val throwable: Throwable? = null) {
    data class NetworkError(override val throwable: Throwable?) : AppError(throwable = throwable)

    data class UnexpectedError(override val throwable: Throwable?) : AppError(throwable = throwable)

    //The error belong to specific feature should be extends this class to create particular error type
    open class FeatureError(msg: String? = null, ex: Throwable? = null) : AppError(throwable = ex)
}
