package com.synrgyacademy.data.remote.retrofit

import com.synrgyacademy.data.remote.request.ChangePasswordRequest
import com.synrgyacademy.data.remote.request.LoginRequest
import com.synrgyacademy.data.remote.request.OTPRequest
import com.synrgyacademy.data.remote.request.RegisterRequest
import com.synrgyacademy.data.remote.response.auth.AccountVerificationResponse
import com.synrgyacademy.data.remote.response.auth.LoginResponse
import com.synrgyacademy.data.remote.response.auth.RegenerateOTPResponse
import com.synrgyacademy.data.remote.response.auth.RegisterResponse
import com.synrgyacademy.data.remote.response.auth.VerifiedOTP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
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

    @PUT("auth/verify-account-forgot")
    suspend fun verifiedOTP(
        @Query("email") email: String,
        @Body otpRequest: String
    ): Response<VerifiedOTP>

    @PATCH("auth/change-password")
    suspend fun changePassword(
        @Query("email") email: String,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<VerifiedOTP>
}