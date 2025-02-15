package com.example.psychoremastered.presentation.session_booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.domain.model.UnavailableTime
import com.example.psychoremastered.domain.use_case.GetClientUseCase
import com.example.psychoremastered.domain.use_case.GetCurrentUserUseCase
import com.example.psychoremastered.domain.use_case.GetUnavailableTimeUseCase
import com.example.psychoremastered.domain.use_case.SaveUnavailableTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BookSessionViewModel @Inject constructor(
    private val getUnavailableTimeUseCase: GetUnavailableTimeUseCase,
    private val saveUnavailableTimeUseCase: SaveUnavailableTimeUseCase,
    getCurrentUserUseCase: GetCurrentUserUseCase,
    getClientUseCase: GetClientUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookSessionState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUserUseCase()?.let { user ->
                getClientUseCase(user.userId).firstOrNull()?.let { client ->
                    _state.update {
                        it.copy(
                            clientEmail = client.email,
                            clientAvatarUri = client.avatarUri,
                            clientDisplayName = client.displayName
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: BookSessionEvent) {
        when (event) {
            is BookSessionEvent.GetUnavailableTime -> getUnavailableTime(
                event.therapistId,
                event.date
            )

            is BookSessionEvent.SetChosenTime -> setChosenTime(event.chosenDay, event.chosenTime)

            is BookSessionEvent.CalculateTotalPay -> calculateTotalPay(event.sessionPrice)

            is BookSessionEvent.SaveChosenTime -> saveChosenTime(
                therapistId = event.therapistId
            )
        }
    }

    private fun setChosenTime(chosenDay: LocalDate, chosenTime: LocalTime) {
        val chosenDateTime = LocalDateTime.of(chosenDay, chosenTime)
        val formattedChosenDateTime =
            chosenDateTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm"))
        _state.update {
            it.copy(
                chosenDate = chosenDay.toString(),
                chosenTime = chosenTime.toString(),
                sessionDate = "$formattedChosenDateTime - " + chosenTime.plusMinutes(30)
            )
        }
    }

    private fun saveChosenTime(therapistId: String) {
        viewModelScope.launch {
            state.value.chosenDate?.let { day ->
                val unavailableTimes =
                    getUnavailableTimeUseCase(therapistId, day).firstOrNull() ?: emptyList()
                state.value.chosenTime?.let { time ->
                    _state.update {
                        it.copy(
                            unavailableTimes = (unavailableTimes + time).map { strTime ->
                                LocalTime.parse(strTime, DateTimeFormatter.ofPattern("HH:mm"))
                            },
                            sessionDate = ""
                        )
                    }
                    saveUnavailableTimeUseCase(
                        UnavailableTime(
                            therapistId = therapistId,
                            date = day,
                            unavailableTimes = unavailableTimes + time
                        )
                    )
                }
            }
        }
    }

    private fun calculateTotalPay(sessionPrice: String) {
        val bookingFees = (sessionPrice.toInt() * Constants.BOOKING_FEES_PERCENTAGE)
        val totalPay = sessionPrice.toInt() + bookingFees
        _state.update {
            it.copy(
                sessionPrice = sessionPrice,
                bookingFees = "%.1f".format(bookingFees),
                totalPay = totalPay.toString()
            )
        }
    }

    private fun getUnavailableTime(therapistId: String, date: String) {
        _state.update {
            it.copy(
                sessionDate = ""
            )
        }
        viewModelScope.launch {
            getUnavailableTimeUseCase(therapistId, date).firstOrNull()?.let { unavailableTimes ->
                _state.update {
                    it.copy(
                        unavailableTimes = unavailableTimes.map { strTime ->
                            LocalTime.parse(strTime, DateTimeFormatter.ofPattern("HH:mm"))
                        }
                    )
                }
            }
        }
    }
}