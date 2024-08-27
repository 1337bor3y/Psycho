package com.example.psychoremastered.domain.model

data class Degree(
    val id: Int,
    val university: String,
    val speciality: String,
    val admissionYear: String,
    val graduationYear: String,
    val documentImage: String
)