package com.example.groovyspotify.di

import com.example.groovyspotify.model.services.AccountService
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.LogService
import com.example.groovyspotify.model.services.StorageService
import com.example.groovyspotify.model.services.impl.AccountServiceImpl
import com.example.groovyspotify.model.services.impl.FirestoreServiceImpl
import com.example.groovyspotify.model.services.impl.LogServiceImpl
import com.example.groovyspotify.model.services.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideFirestoreService(impl: FirestoreServiceImpl): FirestoreService


    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService


}