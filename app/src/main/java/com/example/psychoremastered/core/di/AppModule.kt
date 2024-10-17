package com.example.psychoremastered.core.di

import android.app.Application
import android.content.Context
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.data.local.preference.DataStoreManager
import com.example.psychoremastered.data.local.preference.PreferenceManager
import com.example.psychoremastered.data.local.room.TherapistDao
import com.example.psychoremastered.data.local.room.TherapistDatabase
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTherapistDatabase(@ApplicationContext context: Context): TherapistDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TherapistDatabase::class.java,
            name = Constants.ROOM_THERAPIST_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTherapistDao(therapistDatabase: TherapistDatabase): TherapistDao {
        return therapistDatabase.therapistDao
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabaseInstance(): FirebaseDatabase {
        return Firebase.database
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageInstance(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    @Singleton
    fun providePreferenceManager(application: Application): PreferenceManager {
        return DataStoreManager(application)
    }

    @Provides
    @Singleton
    fun providePagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = Constants.PAGE_SIZE
        )
    }

    @Provides
    @Singleton
    fun provideGooglePaymentsClient(application: Application): PaymentsClient {
        return Wallet.getPaymentsClient(
            application,
            Wallet.WalletOptions.Builder()
                .setEnvironment(Constants.PAYMENTS_ENVIRONMENT)
                .build()
        )
    }
}