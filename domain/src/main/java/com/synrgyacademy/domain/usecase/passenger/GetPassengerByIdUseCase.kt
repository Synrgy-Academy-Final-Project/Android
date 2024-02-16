package com.synrgyacademy.domain.usecase.passenger

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.passenger.PassengerDataModel
import com.synrgyacademy.domain.repository.PassengerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPassengerByIdUseCase @Inject constructor(
    private val repository: PassengerRepository
) {
    operator fun invoke(id: Int): Flow<Resource<PassengerDataModel>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.getPassengerById(id)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}