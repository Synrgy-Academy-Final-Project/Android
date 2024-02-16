package com.synrgyacademy.data.remote.di


import com.synrgyacademy.data.BuildConfig
import com.synrgyacademy.data.remote.retrofit.AirportService
import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.remote.retrofit.TourismService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

    @Provides
    @Singleton
    fun provideOkhttpClient(@Named("loggingInterceptor") loggingInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Named("backend_java")
    fun provideRetrofitBackendJava(
        client: OkHttpClient
    ): Retrofit {
        val baseUrl = BuildConfig.API_KEY
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("backend_fsw")
    fun provideRetrofitBackendFSW(
        client: OkHttpClient
    ): Retrofit {
        val baseUrl = BuildConfig.API_KEY_FSW
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(
        @Named("backend_java") retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideAirportService(
        @Named("backend_java") retrofit: Retrofit
    ): AirportService = retrofit.create(AirportService::class.java)

    @Provides
    @Singleton
    fun provideTourismService(
        @Named("backend_fsw") retrofit: Retrofit
    ): TourismService = retrofit.create(TourismService::class.java)
}