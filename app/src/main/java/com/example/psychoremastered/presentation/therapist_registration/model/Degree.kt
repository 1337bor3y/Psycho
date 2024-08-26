package com.example.psychoremastered.presentation.therapist_registration.model

data class Degree(
    val id: Int,
    val university: String,
    val speciality: String,
    val admissionYear: String,
    val graduationYear: String,
    val documentImage: String
)