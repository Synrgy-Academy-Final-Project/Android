package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.auth.LoginDataModel
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<LoginDataModel>> =
        flow {
            emit(Resource.Loading)
            try {
                val result = authRepository.login(email, password)
                authRepository.createSession()
                authRepository.saveUser(result)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }.flowOn(Dispatchers.IO)
}