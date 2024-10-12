package com.example.psychoremastered.presentation.session_booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.presentation.session_booking.component.Calendar
import com.example.psychoremstered.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSessionBottomSheet(
    therapistId: String,
    viewModel: BookSessionViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    ModalBottomSheet(
        containerColor = colorResource(id = R.color.grey_white),
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Review & Book",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 10.dp),
                text = "Appointment",
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                    text = "Patient info",
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = "client.avatarUri",
                            placeholder = painterResource(id = R.drawable.placeholder)
                        ),
                        contentDescription = "Document image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(125.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Client display name",
                            style = MaterialTheme.typography.bodyLarge
                                .copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Client email",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 10.dp),
                text = "Available time",
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                    text = "Time & Date",
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold)
                )
                Calendar(
                    therapistId = therapistId,
                    state = state,
                    onEvent = onEvent
                )
            }
        }
    }
}

