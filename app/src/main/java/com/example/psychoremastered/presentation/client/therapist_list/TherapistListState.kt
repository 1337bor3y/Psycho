package com.example.psychoremastered.presentation.client.therapist_list

import com.example.psychoremastered.domain.model.Therapist

data class TherapistListState(
    val isLoading: Boolean = false,
    val therapists: List<Therapist> = emptyList(),
    val error: String = ""
)