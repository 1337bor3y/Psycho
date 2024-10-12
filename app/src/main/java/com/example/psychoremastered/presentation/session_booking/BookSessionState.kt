package com.example.psychoremastered.presentation.session_booking

import java.time.LocalTime

data class BookSessionState(
    val unavailableTimes: List<LocalTime> = emptyList()
)
