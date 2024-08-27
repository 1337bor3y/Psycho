package com.example.psychoremastered.core.di

import android.content.Context
import androidx.room.Room
import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.data.local.TherapistDao
import com.example.psychoremastered.data.local.TherapistDatabase
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
            name = Constants.THERAPIST_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTherapistDao(therapistDatabase: TherapistDatabase): TherapistDao {
        return therapistDatabase.therapistDao
    }
}