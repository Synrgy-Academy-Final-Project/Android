package com.synrgyacademy.domain.repository

import com.synrgyacademy.domain.model.airport.FilterDataModel
import com.synrgyacademy.domain.model.auth.AuthDataModel
import com.synrgyacademy.domain.model.auth.LoginDataModel
import com.synrgyacademy.domain.model.auth.UserDataDataModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(fullName: String, email: String, password: String): AuthDataModel
    suspend fun regenerateOTP(email: String)
    suspend fun verifyAccount(email: String, otp: String): LoginDataModel
    suspend fun login(email: String, password: String): LoginDataModel
    suspend fun refreshToken(refreshToken: String): LoginDataModel
    suspend fun isLogin(): Flow<Boolean>
    suspend fun logout()
    suspend fun createSession()
    suspend fun saveUser(userData: LoginDataModel)
    suspend fun forgotPassword(email: String)
    suspend fun verifiedOTP(email: String, otp: String): String

    suspend fun changePassword(
        email: String,
        token: String,
        newPassword: String,
        confirmationPassword: String
    )

    suspend fun getUserData(): Flow<UserDataDataModel>

    suspend fun getFilterData(): FilterDataModel

    suspend fun saveFilterData(filterDataModel: FilterDataModel)

    suspend fun deleteFilterData()

    suspend fun saveNotification(isActive: Boolean)

    suspend fun getNotification(): Boolean
}