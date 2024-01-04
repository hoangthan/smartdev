package com.smartdev.data.core.model

sealed class AppError(open val msg: String? = null) : Throwable(msg) {
    data class NetworkError(override val msg: String?) : AppError(msg)

    data class UnexpectedError(override val msg: String?) : AppError(msg)

    //The error belong to specific feature should be extends this class to create particular error type
    open class FeatureError(msg: String? = null) : AppError(msg)
}
