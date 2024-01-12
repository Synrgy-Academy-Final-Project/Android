package com.synrgyacademy.domain.di

import com.synrgyacademy.domain.repository.AuthRepository
import com.synrgyacademy.domain.usecase.auth.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase =
        RegisterUseCase(authRepository)

}