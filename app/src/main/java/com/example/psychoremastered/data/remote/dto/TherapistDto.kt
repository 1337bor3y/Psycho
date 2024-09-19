package com.example.psychoremastered.data.remote.dto

data class TherapistDto(
    val id: String,
    val avatarUri: String,
    val email: String,
    val displayName: String,
    val specializations: List<String>,
    val workFields: List<String>,
    val languages: List<String>,
    val description: String,
    val price: String,
    val hasDegree: Boolean,
    val degrees: List<DegreeDto>
)
