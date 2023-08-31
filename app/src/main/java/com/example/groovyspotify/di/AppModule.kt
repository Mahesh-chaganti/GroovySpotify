package com.example.groovyspotify.di

import com.example.groovyspotify.data.utils.SpotifyConstant
import com.example.groovyspotify.network.SpotifyApi
import com.example.groovyspotify.network.SpotifyTokenService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
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
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun providesFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun providesStorage(): FirebaseStorage = Firebase.storage


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