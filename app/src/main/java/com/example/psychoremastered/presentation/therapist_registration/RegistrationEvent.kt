package com.example.psychoremastered.presentation.therapist_registration

import com.example.psychoremastered.domain.model.User

sealed interface RegistrationEvent {
    data class AddSpecialization(val specialization: String) : RegistrationEvent
    data class RemoveSpecialization(val specialization: String) : RegistrationEvent
    data class AddWorkField(val workField: String) : RegistrationEvent
    data class RemoveWorkField(val workField: String) : RegistrationEvent
    data class AddLanguage(val language: String) : RegistrationEvent
    data class RemoveLanguage(val language: String) : RegistrationEvent
    data class SetDescription(val description: String) : RegistrationEvent
    data class SetPrice(val price: String) : RegistrationEvent
    data class SetUniversity(val university: String) : RegistrationEvent
    data class SetSpeciality(val speciality: String) : RegistrationEvent
    data class SetAdmissionYear(val admissionYear: String) : RegistrationEvent
    data class SetGraduationYear(val graduationYear: String) : RegistrationEvent
    data class SetDocumentImage(val documentImage: String) : RegistrationEvent
    data class AddDegree(val degreeId: Int) : RegistrationEvent
    data class RemoveDegree(val degreeId: Int) : RegistrationEvent
    data class SetUser(val user: User) : RegistrationEvent
    data object SaveTherapist : RegistrationEvent
}