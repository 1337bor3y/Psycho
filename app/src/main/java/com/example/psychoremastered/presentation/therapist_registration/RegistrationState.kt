package com.example.psychoremastered.presentation.therapist_registration

import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.User

data class RegistrationState(
    val authError: String? = null,
    val user: User? = null,
    val specializations: MutableSet<String> = mutableSetOf(),
    val workFields: MutableSet<String> = mutableSetOf(),
    val languages: MutableSet<String> = mutableSetOf(),
    val description: String = "",
    val price: String = "",
    val university: String = "",
    val speciality: String = "",
    val admissionYear: String = "",
    val graduationYear: String = "",
    val documentImage: String = "",
    val degrees: ArrayList<Degree> = arrayListOf()
)