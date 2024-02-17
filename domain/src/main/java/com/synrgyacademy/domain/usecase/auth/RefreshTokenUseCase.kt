package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.auth.LoginDataModel
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(refreshToken: String): Flow<Resource<LoginDataModel>> = flow {
        emit(Resource.Loading)
        try {
            val result = authRepository.refreshToken(refreshToken)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}