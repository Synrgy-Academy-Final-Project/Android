package com.synrgyacademy.domain.repository

import com.synrgyacademy.domain.model.auth.LoginDataModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(fullName: String, email: String, password: String)
    suspend fun regenerateOTP(email: String)
    suspend fun verifyAccount(email: String, otp: String)
    suspend fun login(email: String, password: String) : LoginDataModel

    suspend fun isLogin(): Boolean

    suspend fun createSession()

    suspend fun saveUser(userData: LoginDataModel)
}