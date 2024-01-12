package com.synrgyacademy.data.di

import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.repository.AuthRepositoryImpl
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
    fun provideAuthRepository(authService: AuthService): AuthRepository =
        AuthRepositoryImpl(authService)

}