package com.synrgyacademy.domain.usecase.filter

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.FilterDataModel
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilterSearchUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<Resource<FilterDataModel>> = flow {
        emit(Resource.Loading)
        try {
            val result = authRepository.getFilterData()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}