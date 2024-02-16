package com.synrgyacademy.domain.usecase.user

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.UserDetailDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserDetailByIdUseCase @Inject constructor(
    private val repository: AirportRepository
) {
    operator fun invoke(id: String): Flow<Resource<UserDetailDataModel>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.getUserDetailById(id)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}