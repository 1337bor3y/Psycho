package com.example.psychoremastered.domain.model

data class Therapist(
    val id: Int,
    val specializations: List<String>,
    val workFields: List<String>,
    val languages: List<String>,
    val description: String,
    val price: String,
    val degrees: List<Degree>
)