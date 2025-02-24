package com.example.psychoremastered.presentation.session_booking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime
import java.time.Year

@Composable
fun Calendar(
    unavailableTimes: List<LocalTime>,
    fetchUnavailableTime: (selectedDate: String) -> Unit,
    setChosenTime: (chosenDay: LocalDate, chosenTime: LocalTime) -> Unit
) {
    var currentDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var selectedTime by rememberSaveable { mutableStateOf<LocalTime?>(null) }
    var selectedDay by rememberSaveable { mutableStateOf(currentDate) }
    var daysOfWeek by rememberSaveable { mutableStateOf(generateDaysOfWeek(currentDate)) }

    LaunchedEffect(selectedDay) {
        fetchUnavailableTime(
            selectedDay.toString()
        )
    }
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (currentDate.month != LocalDate.now().month) {
                IconButton(onClick = {
                    currentDate = currentDate.minusMonths(1)
                    daysOfWeek = generateDaysOfWeek(currentDate)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous"
                    )
                }
            }
            Text(
                text = "${
                    currentDate.month.name.lowercase()
                        .replaceFirstChar { it.uppercase() }
                } ${currentDate.year}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            IconButton(onClick = {
                currentDate = currentDate.plusMonths(1)
                daysOfWeek = generateDaysOfWeek(currentDate)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(daysOfWeek.size) { index ->
                val day = daysOfWeek[index]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            selectedDay = day
                            currentDate = day
                            selectedTime = null
                        }
                        .background(
                            if (selectedDay == day) MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.2f
                            )
                            else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = day.dayOfWeek.name.take(3),
                        style =
                        if (selectedDay == day) MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ) else MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = day.dayOfMonth.toString(),
                        style =
                        if (selectedDay == day) MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ) else MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        TimeSlotRow(
            startTime = if (LocalDate.now() == currentDate) {
                LocalTime.now().hour + 1
            } else {
                8
            },
            selectedTime = selectedTime,
            unavailableTimes = unavailableTimes,
            onTimeSelected = {
                selectedTime = it
                setChosenTime(selectedDay, it)
            })
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Selected time:")
                }
                append("\n$selectedDay ${selectedTime ?: ""}")
            },
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

fun generateDaysOfWeek(current: LocalDate): List<LocalDate> {
    val daysInMonth = current.month.length(Year.isLeap(current.year.toLong()))
    return if (current.year == LocalDate.now().year && current.month == LocalDate.now().month) {
        (LocalDate.now().dayOfMonth..daysInMonth)
            .map { current.withDayOfMonth(it) }
    } else {
        (1..daysInMonth)
            .map { current.withDayOfMonth(it) }
    }
}

@Composable
fun TimeSlotRow(
    startTime: Int,
    selectedTime: LocalTime?,
    unavailableTimes: List<LocalTime>,
    onTimeSelected: (LocalTime) -> Unit
) {
    val timeSlots = generateTimeSlots(startTime, 20, 45)

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(timeSlots.size) { index ->
            val time = timeSlots[index]
            val isUnavailable = unavailableTimes.contains(time)
            TimeSlotItem(
                time = time,
                isSelected = time == selectedTime,
                isUnavailable = isUnavailable,
                onClick = { if (!isUnavailable) onTimeSelected(time) }
            )
        }
    }
}

@Composable
fun TimeSlotItem(
    time: LocalTime,
    isSelected: Boolean,
    isUnavailable: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clickable(enabled = !isUnavailable) { onClick() }
            .background(
                when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    isUnavailable -> Color.LightGray
                    else -> Color.White
                },
                RoundedCornerShape(8.dp)
            )
            .border(
                2.dp,
                if (isUnavailable) Color.LightGray else MaterialTheme.colorScheme.primary,
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = time.toString(),
            fontSize = 16.sp,
            color = when {
                isUnavailable -> Color.Gray
                isSelected -> Color.White
                else -> Color.Black
            }
        )
    }
}

fun generateTimeSlots(startHour: Int, endHour: Int, intervalMinutes: Int): List<LocalTime> {
    val correctedEndHour = if (endHour == 24) 23 else endHour
    val endTime = LocalTime.of(correctedEndHour, if (endHour == 24) 59 else 0)

    return generateSequence(LocalTime.of(minOf(startHour, 23), 0)) { it.plusMinutes(intervalMinutes.toLong()) }
        .takeWhile { it <= endTime }
        .toList()
}