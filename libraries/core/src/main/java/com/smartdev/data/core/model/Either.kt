package com.smartdev.data.core.model

/**
 * Functional programming conventional the result of operator always be True/False
 */
sealed class Either<out L, out R> {
    /**
     * Represent for Error type
     */
    data class Left<out L>(val err: L) : Either<L, Nothing>()

    /**
     * Represent for Success type
     */
    data class Right<out R>(val data: R) : Either<Nothing, R>()

    /**
     * Return the result is this Either is Right or not
     */
    val isRight get() = this is Right<R>

    /**
     * Return the result is this Either is Left or not
     */
    val isLeft get() = this is Left<L>

    /**
     * Add the callback when the stream is called, do not care about the type of data
     */
    suspend fun onEach(func: suspend () -> Unit): Either<L, R> = this.apply { func() }

    /**
     * Add the callback when the current result is Right
     */
    suspend fun <L, R> Either<L, R>.onSuccess(
        funcR: suspend (success: R) -> Unit
    ): Either<L, R> = this.apply { if (this is Right) funcR(data) }

    /**
     * Add the callback when the current result is Left
     */
    suspend fun <L, R> Either<L, R>.onFailure(
        funcL: suspend (failure: L) -> Unit
    ): Either<L, R> = this.apply { if (this is Left) funcL(err) }

    /**
     * Get the data by casting the type as Right.
     * If the type is not right, the exception gonna be thrown
     */
    fun <L, R> Either<L, R>.get(value: R): R = (this as Right<R>).data

    /**
     * Get the data by casting the type as Right.
     * If the type is not right, the default value gonna be returned
     */
    fun <L, R> Either<L, R>.getOrElse(value: R): R = when (this) {
        is Left -> value
        is Right -> data
    }

    /**
     * Get the data by casting the type as Right.
     * If the type is not right the null value gonna be returned
     */
    fun <L, R> Either<L, R>.getOrNull(): R? = when (this) {
        is Left -> null
        is Right -> data
    }

    /**
     * Convert the type of Right if the current result is Right.
     * Unless, return the Left value as error
     */
    suspend fun <T, L, R> Either<L, R>.map(func: suspend (R) -> T): Either<L, T> = when (this) {
        is Left -> Left(err)
        is Right -> Right(func(data))
    }

    /**
     * Convert the type of Right if the current result is Right.
     * Unless, return the Left value as error
     */
    suspend fun <T, L, R> Either<L, R>.flatMap(fn: suspend (R) -> Either<L, T>): Either<L, T> =
        when (this) {
            is Left -> Left(err)
            is Right -> fn(data)
        }

    /**
     * Put both of 2 callbacks for Left and Right type and convert
     */
    suspend fun <T> fold(funcL: suspend (L) -> Nothing, funcR: suspend (R) -> T): Either<L, T> =
        when (this) {
            is Left -> funcL(err)
            is Right -> Right(funcR(data))
        }
}
