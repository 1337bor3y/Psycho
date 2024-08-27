package com.example.psychoremastered.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.psychoremastered.data.local.model.TherapistEntity

@Database(
    entities = [TherapistEntity::class],
    version = 1
)
abstract class TherapistDatabase: RoomDatabase() {

    abstract val therapistDao: TherapistDao
}