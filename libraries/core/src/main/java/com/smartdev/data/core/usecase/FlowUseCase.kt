package com.smartdev.data.core.usecase

import com.smartdev.data.core.model.Either
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<PARAM, L : Throwable, R> {
    operator fun invoke(param: PARAM): Flow<Either<L, R>>
}
