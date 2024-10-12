package com.example.psychoremastered.data.remote.dto

data class UnavailableTimeDto(
    val therapistId: String = "",
    val date: String = "",
    val unavailableTimes: List<String> = emptyList(),
)