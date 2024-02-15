package com.synrgyacademy.domain.usecase.airport

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMinimumPriceUseCase @Inject constructor(
    private val repository: AirportRepository
) {
    operator fun invoke(
        fromAirportCode: String,
        toAirportCode: String,
        departureDate: String,
    ): Flow<Resource<List<MinimumDataModel>>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.getMinimumPrice(fromAirportCode, toAirportCode, departureDate)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}