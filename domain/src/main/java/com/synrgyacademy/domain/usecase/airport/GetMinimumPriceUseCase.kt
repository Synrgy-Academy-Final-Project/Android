package com.synrgyacademy.domain.usecase.airport

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.domain.model.query.MinimumPriceQuery
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMinimumPriceUseCase @Inject constructor(
    private val repository: AirportRepository
) {
    operator fun invoke(minimumPriceQuery: MinimumPriceQuery): Flow<Resource<List<MinimumDataModel>>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.getMinimumPrice(minimumPriceQuery)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}