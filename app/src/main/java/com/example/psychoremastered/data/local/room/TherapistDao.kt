package com.example.psychoremastered.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.psychoremastered.data.local.room.entity.TherapistEntity

@Dao
interface TherapistDao {

    @Upsert
    suspend fun upsertFavouriteTherapist(therapist: TherapistEntity)

    @Query("SELECT * FROM therapistentity")
    suspend fun getFavouriteTherapists(): List<TherapistEntity>

    @Query("DELETE FROM therapistentity WHERE therapist_id = :therapistId")
    suspend fun removeFavouriteTherapist(therapistId: String)
}