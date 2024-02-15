package com.synrgyacademy.domain.usecase.airport

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAirportUseCase @Inject constructor(
    private val repository: AirportRepository
) {
    operator fun invoke(
        query: String? = null
    ): Flow<Resource<List<AirportDataModel>>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.getAirportList(query)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}