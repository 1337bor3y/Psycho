package com.example.psychoremastered.data.local

import androidx.room.Database
import com.example.psychoremastered.data.local.model.TherapistEntity

@Database(
    entities = [TherapistEntity::class],
    version = 1
)
abstract class TherapistDatabase {

    abstract val therapistDao: TherapistDao
}