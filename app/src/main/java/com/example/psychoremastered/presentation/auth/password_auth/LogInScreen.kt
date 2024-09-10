package com.example.psychoremastered.presentation.auth.password_auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psychoremastered.presentation.auth.AuthEvent
import com.example.psychoremastered.presentation.auth.AuthState
import com.example.psychoremstered.R

@Composable
fun LogInScreen(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var isErrorEmail by rememberSaveable {
        mutableStateOf(false)
    }
    var isErrorPassword by rememberSaveable {
        mutableStateOf(false)
    }
    var emailError by rememberSaveable {
        mutableStateOf("")
    }
    var passwordError by rememberSaveable {
        mutableStateOf("")
    }
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Log in",
            style = MaterialTheme
                .typography.headlineMedium
                .copy(
                    color = Color.Black,
                    fontFamily = FontFamily.Serif
                )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = state.email,
            onValueChange = {
                onEvent(
                    AuthEvent.SetEmail(it)
                )
            },
            label = { Text(text = context.getString(R.string.email)) },
            isError = isErrorEmail,
            supportingText = { Text(text = emailError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = state.password,
            onValueChange = {
                onEvent(
                    AuthEvent.SetPassword(it)
                )
            },
            label = { Text(text = context.getString(R.string.password)) },
            isError = isErrorPassword,
            supportingText = { Text(text = passwordError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password"
                        else "Show password"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                isErrorEmail = state.email.isBlank()
                isErrorPassword = state.password.isBlank()
                if (!isErrorEmail && !isErrorPassword) {
                    onEvent(
                        AuthEvent.SignInWithEmailAndPassword
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Blue
            )
        ) {
            Text(
                text = "Log in",
                fontSize = 18.sp
            )
        }
    }
}