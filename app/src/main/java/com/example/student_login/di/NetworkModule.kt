package com.example.student_login.di

import com.example.student_login.api.VuAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

//    val baseUrl: String = "https://nit3213-api-h2b3-latest.onrender.com/"

    val baseUrl: String = "http://10.0.2.2:8000"

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesVuApi(retrofit: Retrofit): VuAPI {
        return retrofit.create(VuAPI::class.java)
    }

}