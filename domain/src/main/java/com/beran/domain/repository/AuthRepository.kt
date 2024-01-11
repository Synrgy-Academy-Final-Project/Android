package com.beran.domain.repository

interface AuthRepository {

    suspend fun register(fullName: String, email: String, password: String)
    suspend fun regenerateOTP(email: String)
    suspend fun verifyAccount(email: String, otp: String)
    suspend fun login(email: String, password: String)

}