package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading)
            try {
                authRepository.forgotPassword(email)
                emit(Resource.Success(Unit))
            }catch (e: Exception){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }.flowOn(Dispatchers.IO)
}