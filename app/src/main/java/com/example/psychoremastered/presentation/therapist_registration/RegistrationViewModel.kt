package com.example.psychoremastered.presentation.therapist_registration

import androidx.lifecycle.ViewModel
import com.example.psychoremastered.domain.model.Degree
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(

) : ViewModel() {

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
                removeDegree(event.degreeId)

            is RegistrationEvent.AddDegree ->
                addDegree(event.degreeId)

            RegistrationEvent.SaveTherapistData ->
                saveTherapistData()
        }
    }

    private fun removeDegree(degreeId: Int) {
        _state.value.degrees.removeIf {
            it.id == degreeId
        }
        val prevDegree = state.value.degrees[degreeId - 1]
        _state.update {
            it.copy(
                university = prevDegree.university,
                speciality = prevDegree.speciality,
                admissionYear = prevDegree.admissionYear,
                graduationYear = prevDegree.graduationYear,
                documentImage = prevDegree.documentImage
            )
        }
    }

    private fun addDegree(degreeId: Int) {
        val index = state.value.degrees.indexOfFirst { it.id == degreeId }
        if (index != -1) {
            state.value.degrees[index] = Degree(
                id = degreeId,
                university = state.value.university,
                speciality = state.value.speciality,
                admissionYear = state.value.admissionYear,
                graduationYear = state.value.graduationYear,
                documentImage = state.value.documentImage
            )
        } else {
            state.value.degrees.add(
                Degree(
                    id = degreeId,
                    university = state.value.university,
                    speciality = state.value.speciality,
                    admissionYear = state.value.admissionYear,
                    graduationYear = state.value.graduationYear,
                    documentImage = state.value.documentImage
                )
            )
        }
    }

    private fun saveTherapistData() {}
}