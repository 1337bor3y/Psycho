package com.example.psychoremastered.presentation.therapist_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.psychoremstered.R

@Composable
fun DegreeImageDialog(
    showDegreeImage: String?,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        text = {
            Image(
                painter = rememberAsyncImagePainter(
                    model = showDegreeImage,
                    placeholder = painterResource(id = R.drawable.placeholder)
                ),
                contentDescription = "Document image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        },
        confirmButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text("Close")
            }
        }
    )
}