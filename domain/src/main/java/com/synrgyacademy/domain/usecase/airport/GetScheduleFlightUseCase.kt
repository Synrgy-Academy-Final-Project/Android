package com.synrgyacademy.domain.usecase.airport

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.ScheduleDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetScheduleFlightUseCase @Inject constructor(
    private val repository: AirportRepository
) {
    operator fun invoke(
        departureCode: String,
        arrivalCode: String,
        departureDate: String,
        airplaneClass: String
    ): Flow<Resource<List<ScheduleDataModel>>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.getScheduleFlight(
                departureCode = departureCode,
                arrivalCode = arrivalCode,
                departureDate = departureDate,
                airplaneClass = airplaneClass
            )
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}