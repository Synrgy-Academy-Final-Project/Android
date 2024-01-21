package com.synrgyacademy.domain.repository

import com.synrgyacademy.domain.model.auth.AuthDataModel
import com.synrgyacademy.domain.model.auth.LoginDataModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(fullName: String, email: String, password: String): AuthDataModel
    suspend fun regenerateOTP(email: String)
    suspend fun verifyAccount(email: String, otp: String) : AuthDataModel
    suspend fun login(email: String, password: String) : LoginDataModel
    suspend fun isLogin(): Boolean
    suspend fun logout()
    suspend fun createSession()
    suspend fun saveUser(userData: LoginDataModel)
    suspend fun forgotPassword(email: String)
    suspend fun verifiedOTP(email: String, otp: String) : String

    suspend fun changePassword(email: String, token: String, newPassword: String, confirmationPassword: String)
}