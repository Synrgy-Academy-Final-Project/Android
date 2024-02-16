package com.synrgyacademy.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.synrgyacademy.common.Constants.DB_NAME
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.local.room.HistorySearchingDao
import com.synrgyacademy.data.local.room.AirplaneDatabase
import com.synrgyacademy.data.local.room.PassengerDao
import com.synrgyacademy.data.local.room.TourismDao
import com.synrgyacademy.data.local.utils.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

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
    fun providesAirplaneDatabase(@ApplicationContext context: Context): AirplaneDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AirplaneDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesAirplaneDao(airplaneDatabase: AirplaneDatabase) : HistorySearchingDao =
        airplaneDatabase.airplaneDao()

    @Provides
    @Singleton
    fun providesPassengerDao(airplaneDatabase: AirplaneDatabase) : PassengerDao =
        airplaneDatabase.passengerDao()

    @Provides
    @Singleton
    fun providesTourismDao(airplaneDatabase: AirplaneDatabase) : TourismDao =
        airplaneDatabase.tourismDao()

}