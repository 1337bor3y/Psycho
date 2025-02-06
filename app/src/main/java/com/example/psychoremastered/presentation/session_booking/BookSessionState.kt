package com.example.psychoremastered.presentation.session_booking

import java.time.LocalTime

data class BookSessionState(
    val isCalendarLoading: Boolean = false,
    val calendarError: String? = null,
    val unavailableTimes: List<LocalTime> = emptyList(),
    val chosenTime: String? = null,
    val chosenDate: String? = null,
    val sessionDate: String = "",
    val clientAvatarUri: String = "",
    val clientDisplayName: String = "",
    val clientEmail: String = "",
    val sessionPrice: String = "",
    val bookingFees: String = "",
    val totalPay: String = ""
)
