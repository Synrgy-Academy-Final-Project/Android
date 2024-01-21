package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, token: String, newPassword: String, confirmPassword: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            val result = authRepository.changePassword(email, token, newPassword, confirmPassword)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}