package com.synrgyacademy.data.repository

import com.synrgyacademy.data.mapper.mapToDomain
import com.synrgyacademy.domain.model.UserData
import com.synrgyacademy.data.pref.SessionManager
import com.synrgyacademy.data.remote.request.LoginRequest
import com.synrgyacademy.data.remote.request.OTPRequest
import com.synrgyacademy.data.remote.request.RegisterRequest
import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.remote.util.SafeApiRequest
import com.synrgyacademy.domain.model.AuthData
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
): AuthRepository, SafeApiRequest() {
    override suspend fun register(fullName: String, email: String, password: String) {
        safeApiRequest {
            val registerRequest = RegisterRequest(
                fullName = fullName,
                email = email,
                password = password
            )
            authService.register(registerRequest)
        }
    }

    override suspend fun regenerateOTP(email: String) {
        safeApiRequest {
            authService.regenerateOTP(email)
        }
    }

    override suspend fun verifyAccount(email: String, otp: String) {
        safeApiRequest {
            val otpRequest =OTPRequest(otp = otp)
            authService.verifyAccount(email, otpRequest)
        }
    }

    override suspend fun login(email: String, password: String): AuthData {
        val loginRequest = LoginRequest(email = email, password = password)
        val loginResponse = safeApiRequest { authService.login(loginRequest) }
        return loginResponse.data?.mapToDomain() ?: throw Exception("Login failed")
    }

    override fun isLogin(): Flow<Boolean> = sessionManager.isLogin()

    override suspend fun createSession() = sessionManager.createSession()
    override fun getSavedData(): Flow<UserData> = sessionManager.getUser()


    override suspend fun saveUser(userData: UserData) {
        sessionManager.saveUser(userData)
    }

    override suspend fun logout() = sessionManager.logout()
}