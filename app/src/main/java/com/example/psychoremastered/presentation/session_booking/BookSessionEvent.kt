package com.example.psychoremastered.presentation.session_booking

sealed interface BookSessionEvent {
    data class GetUnavailableTime(val therapistId: String, val date: String) : BookSessionEvent
}