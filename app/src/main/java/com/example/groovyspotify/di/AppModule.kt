package com.example.groovyspotify.di

import com.example.groovyspotify.data.utils.SpotifyConstant
import com.example.groovyspotify.data.emailAuth.AuthRepository
import com.example.groovyspotify.data.emailAuth.AuthRepositoryImpl
import com.example.groovyspotify.network.SpotifyApi
import com.example.groovyspotify.network.SpotifyTokenService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideSpotifyApi(): SpotifyApi {
        return Retrofit.Builder()
            .baseUrl(SpotifyConstant.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi::class.java)
    }

    @Provides
    fun provideSpotifyTokenService(): SpotifyTokenService {
        return Retrofit.Builder()
            .baseUrl(SpotifyConstant.TOKEN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyTokenService::class.java)
    }



}