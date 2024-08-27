package com.example.psychoremastered.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TherapistEntity(
    @PrimaryKey
    val id: Int,
    val specializations: List<String>,
    @ColumnInfo(name = "work_fields")
    val workFields: List<String>,
    val languages: List<String>,
    val description: String,
    val price: String,
    val degrees: List<DegreeEntity>
)