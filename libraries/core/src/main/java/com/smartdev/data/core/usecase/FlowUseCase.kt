package com.smartdev.data.core.usecase

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<PARAM, L : Throwable, R> {
    operator fun invoke(param: PARAM): Flow<Either<L, R>>
}
