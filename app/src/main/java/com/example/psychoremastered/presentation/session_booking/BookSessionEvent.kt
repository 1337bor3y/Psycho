package com.example.psychoremastered.presentation.session_booking

sealed interface BookSessionEvent {
    data class GetUnavailableTimes(val therapistId: String, val date: String) : BookSessionEvent
}