package com.beran.data.remote.retrofit

import com.beran.data.remote.request.LoginRequest
import com.beran.data.remote.request.OTPRequest
import com.beran.data.remote.request.RegisterRequest
import com.beran.data.remote.response.AccountVerificationResponse
import com.beran.data.remote.response.LoginResponse
import com.beran.data.remote.response.RegenerateOTPResponse
import com.beran.data.remote.response.RegisterResponse
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

    // change the response letter
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}