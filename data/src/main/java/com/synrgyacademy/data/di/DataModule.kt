package com.synrgyacademy.data.di

import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.room.HistorySearchingDao
import com.synrgyacademy.data.local.room.PassengerDao
import com.synrgyacademy.data.remote.retrofit.AirportService
import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.repository.AirportRepositoryImpl
import com.synrgyacademy.data.repository.AuthRepositoryImpl
import com.synrgyacademy.data.repository.PassengerRepositoryImpl
import com.synrgyacademy.domain.repository.AirportRepository
import com.synrgyacademy.domain.repository.AuthRepository
import com.synrgyacademy.domain.repository.PassengerRepository
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
        historySearchingDao: HistorySearchingDao
    ): AirportRepository =
        AirportRepositoryImpl(authService, historySearchingDao)

    @Provides
    @Singleton
    fun providePassengerRepository(
        passengerDao: PassengerDao
    ): PassengerRepository =
        PassengerRepositoryImpl(passengerDao)
}