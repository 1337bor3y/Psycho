package com.example.psychoremastered.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TherapistEntity(
    @PrimaryKey
    val id: Int,
    val specializations: List<String>,
    val workFields: List<String>,
    val languages: List<String>,
    val description: String,
    val price: String,
    val degrees: List<DegreeEntity>
)