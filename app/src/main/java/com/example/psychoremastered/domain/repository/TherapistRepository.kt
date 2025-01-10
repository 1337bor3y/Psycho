package com.example.psychoremastered.domain.repository

import androidx.paging.PagingData
import com.example.psychoremastered.domain.model.Therapist
import kotlinx.coroutines.flow.Flow

interface TherapistRepository {

    suspend fun saveTherapist(therapist: Therapist)

    suspend fun removeTherapist(therapist: Therapist)

    fun getTherapist(therapistId: String): Flow<Therapist?>

    fun getAllTherapists(): Flow<PagingData<Therapist>>

    suspend fun upsertFavouriteTherapist(therapist: Therapist)

    fun getFavouriteTherapists(): Flow<List<Therapist>>

    suspend fun removeFavouriteTherapist(therapistId: String)
}