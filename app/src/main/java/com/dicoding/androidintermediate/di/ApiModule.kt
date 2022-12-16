package com.dicoding.androidintermediate.di

import com.dicoding.androidintermediate.api.Api
import com.dicoding.androidintermediate.api.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideApiService(): Api = ApiConfig.getApiService()
}