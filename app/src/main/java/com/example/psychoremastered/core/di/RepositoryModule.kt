package com.example.psychoremastered.core.di

import com.example.psychoremastered.data.repository.TherapistRepositoryImpl
import com.example.psychoremastered.domain.repository.TherapistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideTherapistRepository(
        therapistRepositoryImpl: TherapistRepositoryImpl
    ): TherapistRepository
}