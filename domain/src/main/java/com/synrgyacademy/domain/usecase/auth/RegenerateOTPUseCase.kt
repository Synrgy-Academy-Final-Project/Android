package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegenerateOTPUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            authRepository.regenerateOTP(email)
            emit(Resource.Success(Unit))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}