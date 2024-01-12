package com.synrgyacademy.data.repository.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.synrgyacademy.data.pref.SessionManager
import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.repository.AuthRepositoryImpl
import com.synrgyacademy.data.utils.dataStore
import com.synrgyacademy.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideSessionManager(dataStore: DataStore<Preferences>): SessionManager =
        SessionManager(dataStore)

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService, sessionManager: SessionManager): AuthRepository =
        AuthRepositoryImpl(authService, sessionManager)
}