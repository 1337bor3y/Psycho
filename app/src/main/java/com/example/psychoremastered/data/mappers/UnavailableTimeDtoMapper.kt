package com.example.psychoremastered.data.mappers

import com.example.psychoremastered.data.remote.dto.UnavailableTimeDto
import com.example.psychoremastered.domain.model.UnavailableTime

fun UnavailableTime.toUnavailableTimeDto(): UnavailableTimeDto {
    return UnavailableTimeDto(
        therapistId = therapistId,
        date = date,
        unavailableTimes = unavailableTimes
    )
}