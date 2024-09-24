package com.example.psychoremastered.presentation.auth.password_auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.psychoremastered.presentation.auth.AuthEvent
import com.example.psychoremastered.presentation.auth.AuthState
import com.example.psychoremstered.R

@Composable
fun SignUpScreen(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var isConfirmPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            onEvent(
                AuthEvent.SetProfileImage(uri?.toString() ?: state.profileImage)
            )
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Sign up",
            style = MaterialTheme
                .typography.headlineMedium
                .copy(
                    color = Color.Black,
                    fontFamily = FontFamily.Serif
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedCard(
            modifier = Modifier
                .width(160.dp)
                .height(140.dp)
                .padding(horizontal = 10.dp),
            border = if (state.isProfileImageValid) {
                BorderStroke(1.dp, Color.Black)
            } else {
                BorderStroke(1.dp, Color.Red)
            },
            onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = state.profileImage),
                    contentDescription = "Document image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
                if (state.profileImage.isBlank()) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize(),
                        imageVector = Icons.Filled.Person,
                        tint = Color.Red,
                        contentDescription = "Person",
                    )
                }
            }
        }
        Text(
            text = state.profileImageError,
            color = MaterialTheme.colorScheme.error,
            fontSize = 14.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = state.firstName,
            onValueChange = {
                onEvent(
                    AuthEvent.SetFirstName(it)
                )
            },
            label = { Text(text = context.getString(R.string.first_name)) },
            isError = !state.isFirstNameValid,
            supportingText = { Text(text = state.firstNameError) }
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = state.surname,
            onValueChange = {
                onEvent(
                    AuthEvent.SetSurname(it)
                )
            },
            label = { Text(text = context.getString(R.string.surname)) },
            isError = !state.isSurnameValid,
            supportingText = { Text(text = state.surnameError) }
        )
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
            isError = !state.isEmailValid,
            supportingText = { Text(text = state.emailError) },
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
            isError = !state.isPasswordValid,
            supportingText = { Text(text = state.passwordError) },
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
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = state.confirmPassword,
            onValueChange = {
                onEvent(
                    AuthEvent.SetConfirmPassword(it)
                )
            },
            label = { Text(text = context.getString(R.string.confirm_password)) },
            isError = !state.isConfirmPasswordValid,
            supportingText = { Text(text = state.confirmPasswordError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = if (isConfirmPasswordVisible) "Hide password"
                        else "Show password"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onEvent(
                    AuthEvent.CreateUserWithEmailAndPassword(navController = navController)
                )
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
                text = "Sign up",
                fontSize = 18.sp
            )
        }
    }
}