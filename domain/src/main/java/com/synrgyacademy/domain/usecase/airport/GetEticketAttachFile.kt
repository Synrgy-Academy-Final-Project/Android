package com.synrgyacademy.domain.usecase.airport

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.repository.AirportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class GetEticketAttachFile @Inject constructor(
    private val airportRepository: AirportRepository
) {
    operator fun invoke(
        token: String,
        idTransaksi: String
    ): Flow<Resource<ResponseBody>> = flow {
        emit(Resource.Loading)
        try {
            val result = airportRepository.getEticketAttachFile(token, idTransaksi)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}