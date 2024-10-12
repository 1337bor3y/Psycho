package com.example.psychoremastered.domain.model

data class UnavailableTime(
    val therapistId: String,
    val date: String,
    val unavailableTimes: List<String>,
)
