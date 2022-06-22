package com.smooth.travelplanner.di

import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import com.smooth.travelplanner.data.repository.FirebaseAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(): BaseAuthRepository {
        return FirebaseAuthRepository()
    }
}