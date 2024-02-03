package com.synrgyacademy.data.di

import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.room.AirplaneDao
import com.synrgyacademy.data.remote.retrofit.AirportService
import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.repository.AirportRepositoryImpl
import com.synrgyacademy.data.repository.AuthRepositoryImpl
import com.synrgyacademy.domain.repository.AirportRepository
import com.synrgyacademy.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        sessionManager: SessionManager
    ): AuthRepository =
        AuthRepositoryImpl(authService, sessionManager)

    @Provides
    @Singleton
    fun provideAirportRepository(
        authService: AirportService,
        airplaneDao: AirplaneDao
    ): AirportRepository =
        AirportRepositoryImpl(authService, airplaneDao)

}