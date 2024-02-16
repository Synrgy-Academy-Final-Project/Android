package com.synrgyacademy.domain.usecase.filter

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.FilterDataModel
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavedFilterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(data: FilterDataModel): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            val result = authRepository.saveFilterData(data)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}