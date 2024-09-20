package com.example.psychoremastered.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.psychoremastered.data.local.room.entity.TherapistEntity
import com.example.psychoremastered.data.local.room.type_converter.DegreeListConverter
import com.example.psychoremastered.data.local.room.type_converter.StringListConverter

@TypeConverters(value = [StringListConverter::class, DegreeListConverter::class])
@Database(
    entities = [TherapistEntity::class],
    version = 1
)
abstract class TherapistDatabase: RoomDatabase() {

    abstract val therapistDao: TherapistDao
}