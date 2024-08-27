package com.example.psychoremastered.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.psychoremastered.data.local.model.TherapistEntity

@Dao
interface TherapistDao {

    @Upsert
    suspend fun upsertTherapist(therapist: TherapistEntity)

    @Query("SELECT * FROM therapistentity WHERE id = :id")
    suspend fun getTherapist(id: Int): TherapistEntity
}