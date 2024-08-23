package com.example.psychoremstered.therapist_registration

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
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
import com.example.psychoremstered.R
import com.example.psychoremstered.therapist_registration.model.DegreeRegistrationPage

@Composable
fun DegreeRegistrationScreen(
    page: DegreeRegistrationPage,
    pageOffset: Float,
    addButtonOnClick: () -> Unit,
    removeButtonOnClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var universityText by rememberSaveable {
        mutableStateOf(page.university)
    }
    var specialityText by rememberSaveable {
        mutableStateOf(page.speciality)
    }
    var admissionText by rememberSaveable {
        mutableStateOf(page.admissionYear)
    }
    var graduationText by rememberSaveable {
        mutableStateOf(page.graduationYear)
    }
    val symbolsLimit = 4
    var admissionSymbolsCount by rememberSaveable {
        mutableStateOf("0")
    }
    var graduationSymbolsCount by rememberSaveable {
        mutableStateOf("0")
    }
    var isErrorAdmission by rememberSaveable {
        mutableStateOf(false)
    }
    var isErrorGraduation by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedImage by rememberSaveable {
        mutableStateOf(page.documentImage)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImage = uri?.toString() ?: ""
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
            text = "Document №${page.documentNumber}",
            fontSize = 20.sp
        )
        Text(text = page.description)
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = universityText,
            onValueChange = {
                universityText = it
            },
            label = { Text(text = context.getString(R.string.name_of_the_educational_institution)) },
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = specialityText,
            onValueChange = {
                specialityText = it
            },
            label = { Text(text = context.getString(R.string.specialty)) },
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
                value = admissionText,
                onValueChange = {
                    if (it.length <= symbolsLimit) {
                        admissionSymbolsCount = it.length.toString()
                        admissionText = it
                        isErrorAdmission = false
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
                value = graduationText,
                onValueChange = {
                    if (it.length <= symbolsLimit) {
                        graduationSymbolsCount = it.length.toString()
                        graduationText = it
                        isErrorGraduation = false
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
            border = BorderStroke(1.dp, Color.Black),
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
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 20.dp, end = 24.dp, bottom = 10.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.Blue),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Blue,
                containerColor = Color.White
            ),
            onClick = { addButtonOnClick() }
        ) {
            Text(text = context.getString(R.string.add_another_document), fontSize = 14.sp)
        }
        if (page.stepNumber > 5) {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 0.dp, end = 24.dp, bottom = 10.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Red),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Red,
                    containerColor = Color.White
                ),
                onClick = { removeButtonOnClick() }
            ) {
                Text(text = context.getString(R.string.remove_this_document), fontSize = 14.sp)
            }
        }
    }
}