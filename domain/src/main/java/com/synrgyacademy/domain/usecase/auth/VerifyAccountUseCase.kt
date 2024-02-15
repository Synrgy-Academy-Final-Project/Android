package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.auth.AuthDataModel
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyAccountUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, otp: String): Flow<Resource<AuthDataModel>> = flow {
        emit(Resource.Loading)
        try {
            val result = authRepository.verifyAccount(email, otp)
            authRepository.createSession()
            authRepository.saveUser(result.toLoginDataModel())
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}