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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.psychoremstered.R

@Preview(showBackground = true)
@Composable
fun SignUpScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var firstName by rememberSaveable {
        mutableStateOf("")
    }
    var surname by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }
    var isErrorFirstName by rememberSaveable {
        mutableStateOf(false)
    }
    var isErrorSurname by rememberSaveable {
        mutableStateOf(false)
    }
    var isErrorEmail by rememberSaveable {
        mutableStateOf(false)
    }
    var isErrorPassword by rememberSaveable {
        mutableStateOf(false)
    }
    var isErrorConfirmPassword by rememberSaveable {
        mutableStateOf(false)
    }
    var firstNameError by rememberSaveable {
        mutableStateOf("")
    }
    var surnameError by rememberSaveable {
        mutableStateOf("")
    }
    var emailError by rememberSaveable {
        mutableStateOf("")
    }
    var passwordError by rememberSaveable {
        mutableStateOf("")
    }
    var confirmPasswordError by rememberSaveable {
        mutableStateOf("")
    }
    var isErrorImage by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedImage by rememberSaveable {
        mutableStateOf("")
    }
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var isConfirmPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImage = uri?.toString() ?: ""
            isErrorImage = false
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
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedCard(
            modifier = Modifier
                .width(160.dp)
                .height(140.dp)
                .padding(horizontal = 10.dp),
            border = if (!isErrorImage) {
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
                    painter = rememberAsyncImagePainter(model = selectedImage),
                    contentDescription = "Document image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
                if (selectedImage.isBlank()) {
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
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = firstName,
            onValueChange = {
                firstName = it
            },
            label = { Text(text = context.getString(R.string.first_name)) },
            isError = isErrorFirstName,
            supportingText = { Text(text = firstNameError) }
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = surname,
            onValueChange = {
                surname = it
            },
            label = { Text(text = context.getString(R.string.surname)) },
            isError = isErrorSurname,
            supportingText = { Text(text = surnameError) }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = email,
            onValueChange = {
                email = it
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
            value = password,
            onValueChange = {
                password = it
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
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            label = { Text(text = context.getString(R.string.confirm_password)) },
            isError = isErrorConfirmPassword,
            supportingText = { Text(text = confirmPasswordError) },
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
                // Validate
                // Sign up
                // Navigate
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