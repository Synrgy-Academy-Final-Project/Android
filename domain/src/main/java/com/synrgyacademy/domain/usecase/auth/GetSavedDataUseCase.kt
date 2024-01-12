package com.synrgyacademy.domain.usecase.auth

import com.synrgyacademy.common.Resource
import com.synrgyacademy.domain.model.UserData
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSavedDataUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(): Flow<Resource<UserData>> = flow {
        emit(Resource.Loading)
        try {
            val value = repository.getSavedData().first()
            emit(Resource.Success(value))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}