package com.example.psychoremstered.therapist_registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.psychoremstered.therapist_registration.model.RegistrationPage

@Composable
fun RegistrationScreen(page: RegistrationPage, pageOffset: Float) {
    val scrollState = rememberScrollState()
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
        Text(text = page.title)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)
        Text(text = page.description)

    }
}