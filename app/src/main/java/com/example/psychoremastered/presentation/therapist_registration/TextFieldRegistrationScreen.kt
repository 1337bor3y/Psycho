package com.example.psychoremastered.presentation.therapist_registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.psychoremastered.presentation.therapist_registration.model.RegistrationPage

@Composable
fun TextFieldRegistrationScreen(
    page: RegistrationPage,
    pageOffset: Float,
    state: RegistrationState,
    onEvent: (RegistrationEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val descriptionSymbolsLimit = 1500
    var symbolsCount by rememberSaveable {
        mutableStateOf(state.description.length.toString())
    }
    var isError by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .verticalScroll(scrollState)
            .graphicsLayer {
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.Blue)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = page.description)
        Spacer(modifier = Modifier.height(10.dp))
        if (page.stepNumber == 3) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(horizontal = 10.dp),
                value = state.description,
                onValueChange = {
                    if (it.length <= descriptionSymbolsLimit) {
                        symbolsCount = it.length.toString()
                        onEvent(
                            RegistrationEvent.SetDescription(it)
                        )
                        isError = false
                    } else {
                        isError = true
                    }
                },
                label = { Text(text = "Description") },
                supportingText = { Text(text = "$symbolsCount/$descriptionSymbolsLimit") },
                isError = isError
            )
        } else if (page.stepNumber == 4) {
            OutlinedTextField(
                modifier = Modifier
                    .width(270.dp),
                value = state.price,
                onValueChange = {
                    onEvent(
                        RegistrationEvent.SetPrice(it)
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End
                ),
                label = { Text(text = "Price") },
                suffix = { Text(text = "$") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}