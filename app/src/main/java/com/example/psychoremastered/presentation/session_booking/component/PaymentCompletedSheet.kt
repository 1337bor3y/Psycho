package com.example.psychoremastered.presentation.session_booking.component

import android.content.Intent
import android.provider.CalendarContract
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.psychoremstered.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCompletedSheet(
    onDismiss: () -> Unit,
    therapistProfileUri: String,
    therapistDisplayName: String,
    therapistSpecialization: String,
    sessionDate: String
) {
    val sheetState = rememberModalBottomSheetState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    ModalBottomSheet(
        containerColor = colorResource(id = R.color.grey_white),
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 10.dp),
                onClick = { onDismiss() }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    tint = Color.Gray,
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = "Cancel"
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                tint = Color.Unspecified,
                painter = painterResource(id = R.drawable.check_circle),
                contentDescription = "Completed"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp),
                text = "Thanks, your booking has been confirmed.",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 20.dp),
                text = "You can also add your consultation date to calendar.",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = therapistProfileUri,
                            placeholder = painterResource(id = R.drawable.placeholder)
                        ),
                        contentDescription = "Therapist image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(85.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp),
                            ) {
                                append(therapistDisplayName)
                            }
                            append("\n${therapistSpecialization}")
                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp),
                        imageVector = Icons.Filled.Videocam,
                        contentDescription = "Video camera"
                    )
                    Text(
                        text = "Online Consultation",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(30.dp),
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = "Calendar"
                    )
                    Text(
                        text = sessionDate,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                onClick = {
                    val startDateTime = LocalDateTime.parse(
                        sessionDate.split(" - ")[0],
                        DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm")
                    )
                    val startMillis =
                        startDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    val intent = Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                        .putExtra(
                            CalendarContract.EXTRA_EVENT_END_TIME,
                            startMillis + 30 * 60 * 1000
                        )
                        .putExtra(CalendarContract.Events.TITLE, "Psychology session")
                        .putExtra(
                            CalendarContract.Events.DESCRIPTION,
                            "Psychology session with $therapistDisplayName"
                        )
                        .putExtra(
                            CalendarContract.Events.AVAILABILITY,
                            CalendarContract.Events.AVAILABILITY_BUSY
                        )
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                        .size(25.dp),
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add"
                )
                Text(
                    text = "Add to calendar",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}