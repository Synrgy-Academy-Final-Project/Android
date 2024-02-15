package com.synrgyacademy.domain.usecase.airport

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.PromotionsDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllPromotionsUseCase @Inject constructor(
    private val repository: AirportRepository
) {
    operator fun invoke(): Flow<Resource<List<PromotionsDataModel>>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.getAllPromotions()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}