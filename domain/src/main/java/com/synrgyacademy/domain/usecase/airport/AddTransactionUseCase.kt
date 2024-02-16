package com.synrgyacademy.domain.usecase.airport

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.TransactionDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import com.synrgyacademy.domain.request.TransactionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: AirportRepository
) {
    operator fun invoke(
        token: String,
        transactionRequest: TransactionRequest
    ): Flow<Resource<TransactionDataModel>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.addTransaction(token, transactionRequest)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}