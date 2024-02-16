package com.synrgyacademy.domain.usecase.airport

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.HistoryTransactionDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHistoryTransactionById @Inject constructor(
    private val airportRepository: AirportRepository
) {
    operator fun invoke(token: String, idTransaction: String): Flow<Resource<HistoryTransactionDataModel>> = flow {
        emit(Resource.Loading)
        try {
            val result = airportRepository.getHistoryTransactionById(token, idTransaction)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}