package com.example.psychoremastered.presentation.therapist_list

import com.example.psychoremastered.domain.model.Therapist

sealed interface TherapistListEvent {
    data class SaveFavouriteTherapist(val therapist: Therapist): TherapistListEvent
    data class RemoveFavouriteTherapist(val therapist: Therapist): TherapistListEvent
}