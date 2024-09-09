package com.example.psychoremastered.presentation.auth.choose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.psychoremastered.presentation.auth.AuthEvent
import com.example.psychoremastered.presentation.auth.AuthState
import com.example.psychoremstered.R

@Composable
fun ChooseScreen(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit
) {
    val context = LocalContext.current
    var openChooseDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (openChooseDialog) {
        ChooseDialog(
            onEvent = onEvent
        ) { openChooseDialog = false }
    }

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text = context.getString(R.string.choose_ask_text),
            style = MaterialTheme
                .typography.headlineMedium
                .copy(
                    color = Color.White,
                    fontFamily = FontFamily.Serif
                )
        )
        Spacer(modifier = Modifier.height(150.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 54.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Blue,
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                openChooseDialog = true
            }) {
            Text(
                text = context.getString(R.string.client),
                style = MaterialTheme
                    .typography.titleLarge
                    .copy(
                        fontFamily = FontFamily.Serif
                    )
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 54.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Blue,
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                openChooseDialog = true
            }) {
            Text(
                text = context.getString(R.string.therapist),
                style = MaterialTheme
                    .typography.titleLarge
                    .copy(
                        fontFamily = FontFamily.Serif
                    )
            )
        }
    }
}