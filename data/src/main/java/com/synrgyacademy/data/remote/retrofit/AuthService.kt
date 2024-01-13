package com.synrgyacademy.data.remote.retrofit

import com.synrgyacademy.data.remote.request.LoginRequest
import com.synrgyacademy.data.remote.request.OTPRequest
import com.synrgyacademy.data.remote.request.RegisterRequest
import com.synrgyacademy.data.remote.response.AccountVerificationResponse
import com.synrgyacademy.data.remote.response.LoginResponse
import com.synrgyacademy.data.remote.response.RegenerateOTPResponse
import com.synrgyacademy.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthService {
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @PUT("auth/verify-account")
    suspend fun verifyAccount(
        @Query("email") email: String,
        @Body otpRequest: OTPRequest
    ): Response<AccountVerificationResponse>

    @PUT("auth/regenerate-otp")
    suspend fun regenerateOTP(
        @Query("email") email: String
    ): Response<RegenerateOTPResponse>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body email: String
    ): Response<LoginResponse>
}