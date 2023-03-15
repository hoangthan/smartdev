package com.smartdev.data.core.usecase

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<PARAM, RESULT> {
    operator fun invoke(param: PARAM): Flow<RESULT>
}
