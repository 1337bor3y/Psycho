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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.presentation.google_pay.GooglePaymentButton
import com.example.psychoremastered.presentation.session_booking.component.Calendar
import com.example.psychoremstered.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSessionBottomSheet(
    viewModel: BookSessionViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    therapist: Therapist
) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    onEvent(BookSessionEvent.CalculateTotalPay(therapist.price))
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
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Start),
                text = "Appointment",
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    text = "Patient info",
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = state.clientAvatarUri,
                            placeholder = painterResource(id = R.drawable.placeholder)
                        ),
                        contentDescription = "Client image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(125.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = state.clientDisplayName,
                            style = MaterialTheme.typography.bodyLarge
                                .copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = state.clientEmail,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.Start),
                text = "Schedule",
                style = MaterialTheme.typography.bodyLarge,
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    text = "Time & Date",
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold)
                )
                Calendar(
                    therapistId = therapist.id,
                    state = state,
                    onEvent = onEvent
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.Start),
                text = "Payment",
                style = MaterialTheme.typography.bodyLarge,
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Bill Details",
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider(thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Session fees:",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$${state.sessionPrice}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Booking fees:",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "\$${state.bookingFees}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 1.dp)
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Total Pay:",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$${state.totalPay}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.End
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            GooglePaymentButton(
                price = state.totalPay,
                payButtonEnabled = state.chosenTime.isNotBlank(),
                therapistProfileUri = therapist.avatarUri,
                therapistDisplayName = therapist.displayName,
                therapistSpecialization = therapist.specializations.joinToString(", "),
                sessionDate = state.chosenTime
            )
        }
    }
}