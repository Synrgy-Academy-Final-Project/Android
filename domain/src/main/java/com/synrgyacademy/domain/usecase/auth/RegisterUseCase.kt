package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.auth.AuthDataModel
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(fullName: String, email: String, password: String): Flow<Resource<AuthDataModel>> =
        flow {
            emit(Resource.Loading)
            try {
                val data = authRepository.register(fullName, email, password)
                emit(Resource.Success(data))
            }catch (e: Exception){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
            .flowOn(Dispatchers.IO)
}