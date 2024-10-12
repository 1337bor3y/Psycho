package com.example.psychoremastered.core.di

import com.example.psychoremastered.data.auth.AuthApi
import com.example.psychoremastered.data.auth.AuthFirebaseSource
import com.example.psychoremastered.data.remote.ClientApi
import com.example.psychoremastered.data.remote.ClientFirebaseDataSource
import com.example.psychoremastered.data.remote.ImageStorageApi
import com.example.psychoremastered.data.remote.ImageStorageFirebase
import com.example.psychoremastered.data.remote.TherapistApi
import com.example.psychoremastered.data.remote.TherapistFirebaseDataSource
import com.example.psychoremastered.data.remote.UnavailableTimeApi
import com.example.psychoremastered.data.remote.UnavailableTimeFirebaseSource
import com.example.psychoremastered.data.repository.AuthRepositoryImpl
import com.example.psychoremastered.data.repository.ClientRepositoryImpl
import com.example.psychoremastered.data.repository.PreferenceRepositoryImpl
import com.example.psychoremastered.data.repository.TherapistRepositoryImpl
import com.example.psychoremastered.data.repository.UnavailableTimeRepositoryImpl
import com.example.psychoremastered.data.repository.ValidationRepositoryImpl
import com.example.psychoremastered.data.validation.EmailPatterValidator
import com.example.psychoremastered.data.validation.PatternValidator
import com.example.psychoremastered.domain.repository.AuthRepository
import com.example.psychoremastered.domain.repository.ClientRepository
import com.example.psychoremastered.domain.repository.PreferenceRepository
import com.example.psychoremastered.domain.repository.TherapistRepository
import com.example.psychoremastered.domain.repository.UnavailableTimeRepository
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
    abstract fun provideEmailPatternValidator(
        emailPatterValidator: EmailPatterValidator
    ): PatternValidator

    @Binds
    @Singleton
    abstract fun provideAuthApi(
        authFirebaseSource: AuthFirebaseSource
    ): AuthApi

    @Binds
    @Singleton
    abstract fun provideClientApi(
        clientFirebaseDataSource: ClientFirebaseDataSource
    ): ClientApi

    @Binds
    @Singleton
    abstract fun provideTherapistApi(
        therapistFirebaseDataSource: TherapistFirebaseDataSource
    ): TherapistApi

    @Binds
    @Singleton
    abstract fun provideStorageApi(
        imageStorageFirebase: ImageStorageFirebase
    ): ImageStorageApi

    @Binds
    @Singleton
    abstract fun provideValidationRepository(
        validationRepositoryImpl: ValidationRepositoryImpl
    ): ValidationRepository

    @Binds
    @Singleton
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun provideClientRepository(
        clientRepositoryImpl: ClientRepositoryImpl
    ): ClientRepository


    @Binds
    @Singleton
    abstract fun provideTherapistRepository(
        therapistRepositoryImpl: TherapistRepositoryImpl
    ): TherapistRepository

    @Binds
    @Singleton
    abstract fun providePreferenceRepository(
        preferenceRepositoryImpl: PreferenceRepositoryImpl
    ): PreferenceRepository

    @Binds
    @Singleton
    abstract fun provideUnavailableTimeApi(
        unavailableTimeFirebaseSource: UnavailableTimeFirebaseSource
    ): UnavailableTimeApi

    @Binds
    @Singleton
    abstract fun provideUnavailableTimeRepository(
        unavailableTimeRepositoryImpl: UnavailableTimeRepositoryImpl
    ): UnavailableTimeRepository
}