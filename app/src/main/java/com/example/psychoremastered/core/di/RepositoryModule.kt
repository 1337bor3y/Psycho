package com.example.psychoremastered.core.di

import com.example.psychoremastered.data.repository.FirebaseAuthRepositoryImpl
import com.example.psychoremastered.data.repository.TherapistRepositoryImpl
import com.example.psychoremastered.data.repository.ValidationRepositoryImpl
import com.example.psychoremastered.data.validation.EmailPatterValidator
import com.example.psychoremastered.data.validation.PatternValidator
import com.example.psychoremastered.domain.repository.AuthRepository
import com.example.psychoremastered.domain.repository.TherapistRepository
import com.example.psychoremastered.domain.repository.ValidationRepository
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

    @Binds
    @Singleton
    abstract fun provideAuthRepository(
        firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun provideValidationRepository(
        validationRepositoryImpl: ValidationRepositoryImpl
    ): ValidationRepository
}