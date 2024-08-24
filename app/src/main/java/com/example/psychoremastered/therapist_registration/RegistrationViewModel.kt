package com.example.psychoremastered.therapist_registration

import androidx.lifecycle.ViewModel
import com.example.psychoremastered.therapist_registration.model.Degree
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.AddLanguage ->
                _state.value.languages.add(event.language)

            is RegistrationEvent.AddSpecialization ->
                _state.value.specializations.add(event.specialization)

            is RegistrationEvent.AddWorkField ->
                _state.value.workFields.add(event.workField)

            is RegistrationEvent.RemoveLanguage ->
                _state.value.languages.remove(event.language)

            is RegistrationEvent.RemoveSpecialization ->
                _state.value.specializations.remove(event.specialization)

            is RegistrationEvent.RemoveWorkField ->
                _state.value.workFields.remove(event.workField)

            is RegistrationEvent.SetAdmissionYear ->
                _state.update {
                    it.copy(
                        admissionYear = event.admissionYear
                    )
                }

            is RegistrationEvent.SetDocumentImage ->
                _state.update {
                    it.copy(
                        documentImage = event.documentImage
                    )
                }

            is RegistrationEvent.SetGraduationYear ->
                _state.update {
                    it.copy(
                        graduationYear = event.graduationYear
                    )
                }

            is RegistrationEvent.SetSpeciality ->
                _state.update {
                    it.copy(
                        speciality = event.speciality
                    )
                }

            is RegistrationEvent.SetUniversity ->
                _state.update {
                    it.copy(
                        university = event.university
                    )
                }

            is RegistrationEvent.RemoveDegree ->
                removeDegree()

            is RegistrationEvent.AddDegree ->
                addDegree()

            is RegistrationEvent.SetDescription ->
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }

            is RegistrationEvent.SetPrice ->
                _state.update {
                    it.copy(
                        price = event.price
                    )
                }

            RegistrationEvent.SaveData -> TODO()
        }
    }

    private fun removeDegree() {
        _state.value.degrees.remove(
            Degree(
                university = _state.value.university,
                speciality = _state.value.speciality,
                admissionYear = _state.value.admissionYear,
                graduationYear = _state.value.graduationYear,
                documentImage = _state.value.documentImage,
            )
        )
        _state.update {
            it.copy(
                university = "",
                speciality = "",
                admissionYear = "",
                graduationYear = "",
                documentImage = ""
            )
        }
    }

    private fun addDegree() {
        _state.value.degrees.add(
            Degree(
                university = _state.value.university,
                speciality = _state.value.speciality,
                admissionYear = _state.value.admissionYear,
                graduationYear = _state.value.graduationYear,
                documentImage = _state.value.documentImage,
            )
        )
    }
}