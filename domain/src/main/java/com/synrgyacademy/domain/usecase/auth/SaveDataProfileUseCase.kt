package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.UserData
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveDataProfileUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(userData: UserData): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            val result = repository.saveUser(userData)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}