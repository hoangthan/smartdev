package com.smartdev.data.core.usecase

import arrow.core.Either
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<PARAM, L, R> {
    operator fun invoke(param: PARAM): Flow<Either<L, R>>
}
