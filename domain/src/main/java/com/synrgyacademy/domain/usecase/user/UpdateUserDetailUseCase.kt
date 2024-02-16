package com.synrgyacademy.domain.usecase.user

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.airport.UserDetailDataModel
import com.synrgyacademy.domain.repository.AirportRepository
import com.synrgyacademy.domain.request.UserDetailRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserDetailUseCase @Inject constructor(
    private val airportRepository: AirportRepository
) {
    operator fun invoke(
        token: String,
        userData: UserDetailRequest
    ): Flow<Resource<UserDetailDataModel>> = flow {
        emit(Resource.Loading)
        try {
            val result = airportRepository.updateUserData(token, userData)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}