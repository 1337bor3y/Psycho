package com.example.psychoremastered.therapist_registration

import com.example.psychoremastered.therapist_registration.model.Degree

data class RegistrationState(
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