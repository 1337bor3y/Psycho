package com.example.psychoremastered.presentation.session_booking

import java.time.LocalDate
import java.time.LocalTime

sealed interface BookSessionEvent {
    data class GetUnavailableTime(
        val therapistId: String,
        val date: String
    ) : BookSessionEvent

    data class SetChosenTime(
        val chosenDay: LocalDate,
        val chosenTime: LocalTime
    ) : BookSessionEvent

    data class CalculateTotalPay(val sessionPrice: String) : BookSessionEvent

    data class SaveChosenTime(
        val therapistId: String
    ) : BookSessionEvent
}