package com.example.psychoremastered.presentation.therapist_registration

import androidx.lifecycle.ViewModel
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.use_case.SaveTherapist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val saveTherapist: SaveTherapist
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
                removeDegree(event.id)

            is RegistrationEvent.AddDegree ->
                addDegree(event.id)

            RegistrationEvent.SaveTherapistData ->
                saveTherapistData()
        }
    }

    private fun saveTherapistData() {
        TODO("Not yet implemented")
    }

    private fun removeDegree(id: Int) {
        _state.value.degrees.removeIf {
            it.id == id
        }
        val prevDegree = _state.value.degrees[id - 1]
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

    private fun addDegree(id: Int) {
        val index = _state.value.degrees.indexOfFirst { it.id == id }
        if (index != -1) {
            _state.value.degrees[index] = Degree(
                id = id,
                university = _state.value.university,
                speciality = _state.value.speciality,
                admissionYear = _state.value.admissionYear,
                graduationYear = _state.value.graduationYear,
                documentImage = _state.value.documentImage
            )
        } else {
            _state.value.degrees.add(
                Degree(
                    id = id,
                    university = _state.value.university,
                    speciality = _state.value.speciality,
                    admissionYear = _state.value.admissionYear,
                    graduationYear = _state.value.graduationYear,
                    documentImage = _state.value.documentImage
                )
            )
        }
    }
}