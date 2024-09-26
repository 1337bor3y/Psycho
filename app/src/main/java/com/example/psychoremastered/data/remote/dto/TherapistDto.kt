package com.example.psychoremastered.data.remote.dto

data class TherapistDto(
    val id: String = "",
    val avatarUri: String = "",
    val email: String = "",
    val displayName: String = "",
    val specializations: List<String> = emptyList(),
    val workFields: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val description: String = "",
    val price: String = "",
    val hasDegree: Boolean = true,
    val degrees: List<DegreeDto> = emptyList()
)
