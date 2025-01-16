package com.example.psychoremastered.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TherapistEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "therapist_id")
    val therapistId: String,
    @ColumnInfo(name = "avatar_uri")
    val avatarUri: String,
    val email: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    val specializations: List<String>,
    @ColumnInfo(name = "work_fields")
    val workFields: List<String>,
    val languages: List<String>,
    val description: String,
    val price: String,
    val hasDegree: Boolean,
    val degrees: List<DegreeEntity>
)