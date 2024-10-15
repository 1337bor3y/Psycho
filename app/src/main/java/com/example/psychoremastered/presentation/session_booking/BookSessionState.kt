package com.example.psychoremastered.presentation.session_booking

import java.time.LocalTime

data class BookSessionState(
    val isCalendarLoading: Boolean = false,
    val calendarError: String? = null,
    val unavailableTimes: List<LocalTime> = emptyList()
)
