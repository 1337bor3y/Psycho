package com.example.psychoremastered.presentation.session_booking

sealed interface BookSessionEvent {
    data class GetUnavailableTime(val therapistId: String, val date: String) : BookSessionEvent
    data class SetChosenTime(val time: String) : BookSessionEvent
    data class CalculateTotalPay(val sessionPrice: String) : BookSessionEvent
}