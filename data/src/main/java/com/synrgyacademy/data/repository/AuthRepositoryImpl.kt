package com.synrgyacademy.data.repository

import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.mapper.toAuthDataModel
import com.synrgyacademy.data.mapper.toLoginDataModel
import com.synrgyacademy.data.mapper.toUserData
import com.synrgyacademy.data.remote.request.ChangePasswordRequest
import com.synrgyacademy.data.remote.request.LoginRequest
import com.synrgyacademy.data.remote.request.OTPRequest
import com.synrgyacademy.data.remote.request.RegisterRequest
import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.remote.util.SafeApiRequest
import com.synrgyacademy.domain.model.auth.AuthDataModel
import com.synrgyacademy.domain.model.auth.LoginDataModel
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) : AuthRepository, SafeApiRequest() {

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): AuthDataModel {
        val registerRequest = RegisterRequest(
            fullName = fullName,
            email = email,
            password = password
        )
        val response = safeApiRequest { authService.register(registerRequest) }
        return response.data!!.toAuthDataModel()
    }

    override suspend fun regenerateOTP(email: String) {
        safeApiRequest {
            authService.regenerateOTP(email)
        }
    }

    override suspend fun verifyAccount(email: String, otp: String) : AuthDataModel {
        val otpRequest = OTPRequest(otp = otp)
        val response = safeApiRequest { authService.verifyAccount(email, otpRequest) }
        return response.data!!.toAuthDataModel()
    }

    override suspend fun login(
        email: String,
        password: String
    ): LoginDataModel {
        val request = LoginRequest(
            email = email,
            password = password
        )
        val response = safeApiRequest { authService.login(request) }
        return response.data!!.toLoginDataModel()
    }

    override suspend fun isLogin(): Boolean = sessionManager.isLogin().first()
    override suspend fun logout() = sessionManager.logout()

    override suspend fun createSession() = sessionManager.createSession()

    override suspend fun saveUser(userData: LoginDataModel) {
        sessionManager.saveUser(userData.toUserData())
    }

    override suspend fun forgotPassword(email: String) {
        safeApiRequest {
            authService.forgotPassword(email)
        }
    }

    override suspend fun verifiedOTP(email: String, otp: String) : String {
        val response = safeApiRequest {  authService.verifiedOTP(email, otp) }
        return response.data!!.token!!
    }

    override suspend fun changePassword(
        email: String,
        token: String,
        newPassword: String,
        confirmationPassword: String
    ) {
        safeApiRequest {
            authService.changePassword(
                email,
                ChangePasswordRequest(token, newPassword, confirmationPassword)
            )
        }
    }

}