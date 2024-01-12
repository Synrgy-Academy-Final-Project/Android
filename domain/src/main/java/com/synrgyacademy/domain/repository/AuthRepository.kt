package com.synrgyacademy.domain.repository

import kotlinx.coroutines.flow.Flow
import com.synrgyacademy.domain.model.UserData
import com.synrgyacademy.domain.model.AuthData

interface AuthRepository {

    suspend fun register(fullName: String, email: String, password: String)
    suspend fun regenerateOTP(email: String)
    suspend fun verifyAccount(email: String, otp: String)
    suspend fun login(email: String, password: String) : AuthData

    fun isLogin(): Flow<Boolean>

    suspend fun createSession()

    fun getSavedData(): Flow<UserData>

    suspend fun saveUser(userData: UserData)

    suspend fun logout()
}