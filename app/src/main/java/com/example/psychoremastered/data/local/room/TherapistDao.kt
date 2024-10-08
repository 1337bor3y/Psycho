package com.example.psychoremastered.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.psychoremastered.data.local.room.entity.TherapistEntity

@Dao
interface TherapistDao {

    @Upsert
    suspend fun upsertTherapist(therapist: TherapistEntity)

    @Query("SELECT * FROM therapistentity WHERE therapist_id = :therapistId")
    suspend fun getTherapist(therapistId: String): TherapistEntity
}