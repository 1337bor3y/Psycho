package com.example.psychoremastered.presentation.session_booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.domain.use_case.GetClientUseCase
import com.example.psychoremastered.domain.use_case.GetCurrentUserUseCase
import com.example.psychoremastered.domain.use_case.GetUnavailableTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BookSessionViewModel @Inject constructor(
    private val getUnavailableTimeUseCase: GetUnavailableTimeUseCase,
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

            is BookSessionEvent.SetChosenTime -> _state.update {
                it.copy(
                    chosenTime = event.time
                )
            }

            is BookSessionEvent.CalculateTotalPay -> calculateTotalPay(event.sessionPrice)
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