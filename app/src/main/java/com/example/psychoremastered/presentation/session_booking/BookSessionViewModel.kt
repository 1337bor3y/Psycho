package com.example.psychoremastered.presentation.session_booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {

    private val _state = MutableStateFlow(BookSessionState())
    val state = _state.asStateFlow()

    fun onEvent(event: BookSessionEvent) {
        when (event) {
            is BookSessionEvent.GetUnavailableTimes -> getUnavailableTimes(
                event.therapistId,
                event.date
            )
        }
    }

    private fun getUnavailableTimes(therapistId: String, date: String) {
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