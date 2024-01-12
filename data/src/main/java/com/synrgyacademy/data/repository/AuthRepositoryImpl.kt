package com.synrgyacademy.data.repository

import com.synrgyacademy.data.remote.request.LoginRequest
import com.synrgyacademy.data.remote.request.OTPRequest
import com.synrgyacademy.data.remote.request.RegisterRequest
import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.remote.util.SafeApiRequest
import com.synrgyacademy.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
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

    override suspend fun login(email: String, password: String) {
        safeApiRequest {
            val loginRequest = LoginRequest(email = email, password = password)
            authService.login(loginRequest)
        }
    }
}