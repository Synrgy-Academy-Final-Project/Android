package com.beran.domain.usecase.auth

import com.beran.common.Resource
import com.beran.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyAccountUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, otp: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            authRepository.verifyAccount(email, otp)
            emit(Resource.Success(Unit))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}