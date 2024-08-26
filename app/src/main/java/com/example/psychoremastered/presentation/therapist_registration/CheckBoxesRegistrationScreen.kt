package com.example.psychoremastered.presentation.therapist_registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.psychoremastered.presentation.therapist_registration.model.RegistrationPage

@Composable
fun CheckBoxesRegistrationScreen(
    page: RegistrationPage,
    pageOffset: Float,
    onEvent: (RegistrationEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val checkedStates = remember {
        mutableStateMapOf<String, Boolean>().apply {
            putAll(page.checkBoxes)
        }
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
        page.checkBoxes.forEach { (key, _) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = checkedStates[key] ?: false,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            when (page.stepNumber) {
                                0 -> { onEvent(RegistrationEvent.AddSpecialization(key)) }
                                1 -> { onEvent(RegistrationEvent.AddWorkField(key)) }
                                2 -> { onEvent(RegistrationEvent.AddLanguage(key)) }
                            }
                        }
                        checkedStates[key] = isChecked
                    }
                )
                Text(key)
            }
        }
    }
}