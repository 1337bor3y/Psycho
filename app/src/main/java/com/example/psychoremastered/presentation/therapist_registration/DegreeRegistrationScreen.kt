package com.example.psychoremastered.presentation.therapist_registration

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.presentation.therapist_registration.model.RegistrationPage
import com.example.psychoremstered.R

@Composable
fun DegreeRegistrationScreen(
    page: RegistrationPage,
    pageOffset: Float,
    degree: Degree,
    onEvent: (RegistrationEvent) -> Unit,
    moveToNextPage: () -> Unit,
    removeOnClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val symbolsLimit = 4
    var admissionSymbolsCount by rememberSaveable(degree) {
        mutableStateOf(degree.admissionYear.length.toString())
    }
    var graduationSymbolsCount by rememberSaveable(degree) {
        mutableStateOf(degree.graduationYear.length.toString())
    }
    var isErrorUniversity by rememberSaveable(degree) {
        mutableStateOf(false)
    }
    var isErrorSeciality by rememberSaveable(degree) {
        mutableStateOf(false)
    }
    var isErrorAdmission by rememberSaveable(degree) {
        mutableStateOf(false)
    }
    var isErrorGraduation by rememberSaveable(degree) {
        mutableStateOf(false)
    }
    var isErrorImage by rememberSaveable(degree) {
        mutableStateOf(false)
    }
    var university by rememberSaveable(degree) {
        mutableStateOf(degree.university)
    }
    var speciality by rememberSaveable(degree) {
        mutableStateOf(degree.speciality)
    }
    var admissionYear by rememberSaveable(degree) {
        mutableStateOf(degree.admissionYear)
    }
    var graduationYear by rememberSaveable(degree) {
        mutableStateOf(degree.graduationYear)
    }
    var selectedImage by rememberSaveable(degree) {
        mutableStateOf(degree.documentImage)
    }
    var isSaved by rememberSaveable {
        mutableStateOf(false)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImage = uri?.toString() ?: selectedImage
            onEvent(
                RegistrationEvent.SetDocumentImage(selectedImage)
            )
            isErrorImage = false
            isSaved = false
        }
    )

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
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            text = "Document №${degree.id + 1}",
            fontSize = 20.sp
        )
        Text(text = page.description)
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = university,
            onValueChange = {
                university = it
                onEvent(
                    RegistrationEvent.SetUniversity(it)
                )
                isErrorUniversity = false
                isSaved = false
            },
            label = { Text(text = context.getString(R.string.name_of_the_educational_institution)) },
            isError = isErrorUniversity
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = speciality,
            onValueChange = {
                speciality = it
                onEvent(
                    RegistrationEvent.SetSpeciality(it)
                )
                isErrorSeciality = false
                isSaved = false
            },
            label = { Text(text = context.getString(R.string.specialty)) },
            isError = isErrorSeciality
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                value = admissionYear,
                onValueChange = {
                    if (it.length <= symbolsLimit) {
                        admissionSymbolsCount = it.length.toString()
                        admissionYear = it
                        onEvent(
                            RegistrationEvent.SetAdmissionYear(it)
                        )
                        isErrorAdmission = false
                        isSaved = false
                    } else {
                        isErrorAdmission = true
                    }
                },
                label = { Text(text = context.getString(R.string.admission_year)) },
                supportingText = { Text(text = "$admissionSymbolsCount/$symbolsLimit") },
                isError = isErrorAdmission,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                value = graduationYear,
                onValueChange = {
                    if (it.length <= symbolsLimit) {
                        graduationSymbolsCount = it.length.toString()
                        graduationYear = it
                        onEvent(
                            RegistrationEvent.SetGraduationYear(it)
                        )
                        isErrorGraduation = false
                        isSaved = false
                    } else {
                        isErrorGraduation = true
                    }
                },
                label = { Text(text = context.getString(R.string.graduation_year)) },
                supportingText = { Text(text = "$graduationSymbolsCount/$symbolsLimit") },
                isError = isErrorGraduation,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(370.dp)
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
                    .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp)),
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
                    Text(
                        text = "Add document photo",
                        color = Color.Black,
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, top = 20.dp, end = 14.dp, bottom = 10.dp)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Green),
                colors = if (isSaved) {
                    ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Green
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        contentColor = Color.Green,
                        containerColor = Color.White
                    )
                },
                onClick = {
                    isErrorUniversity = university.isBlank()
                    isErrorSeciality = speciality.isBlank()
                    isErrorAdmission = admissionYear.isBlank()
                    isErrorGraduation = graduationYear.isBlank()
                    isErrorImage = selectedImage.isBlank()
                    if (!isErrorUniversity && !isErrorSeciality && !isErrorAdmission
                        && !isErrorGraduation && !isErrorImage
                    ) {
                        onEvent(
                            RegistrationEvent.AddDegree(degree.id)
                        )
                        isSaved = true
                    } else {
                        Toast.makeText(context, "Fill in degree!", Toast.LENGTH_SHORT).show()
                        isSaved = false
                    }
                }) {
                if (isSaved) {
                    Icon(
                        imageVector = Icons.Outlined.Done,
                        contentDescription = "Saved",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = if (isSaved) {
                        "Saved"
                    } else {
                        "Save"
                    }, fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                modifier = Modifier
                    .weight(2f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Blue),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Blue,
                    containerColor = Color.White
                ),
                onClick = {
                    if (isSaved) {
                        moveToNextPage()
                    } else {
                        Toast.makeText(
                            context,
                            "Save before adding another degree!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Text(text = context.getString(R.string.add_another_document), fontSize = 14.sp)
            }
        }
        if (degree.id > 0) {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, top = 0.dp, end = 14.dp, bottom = 10.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Red),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Red,
                    containerColor = Color.White
                ),
                onClick = { removeOnClick() }
            ) {
                Text(text = context.getString(R.string.remove_this_document), fontSize = 14.sp)
            }
        }
    }
}