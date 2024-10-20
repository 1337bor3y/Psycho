package com.example.psychoremastered.presentation.google_pay

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.wallet.contract.TaskResultContracts
import com.google.pay.button.PayButton

@Composable
fun GooglePaymentButton(
    googlePaymentViewModel: GooglePaymentViewModel = hiltViewModel(),
    price: String,
    payButtonEnabled: Boolean,
    therapistProfileUri: String,
    therapistDisplayName: String,
    therapistSpecialization: String,
    sessionDate: String
) {
    val state by googlePaymentViewModel.state.collectAsStateWithLifecycle()
    val onEvent = googlePaymentViewModel::onEvent
    val context = LocalContext.current
    val paymentDataLauncher = rememberLauncherForActivityResult(
        contract = TaskResultContracts.GetPaymentDataResult()
    ) { taskResult ->
        when (taskResult.status.statusCode) {
            CommonStatusCodes.SUCCESS -> {
                taskResult.result?.let {
                    onEvent(
                        GooglePaymentEvent.SetPaymentData(it)
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    if (state.notStarted && state.available) {
        PayButton(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = payButtonEnabled,
            onClick = {
                onEvent(
                    GooglePaymentEvent.LoadPaymentData(
                        price = price,
                        onCompleteListener = paymentDataLauncher::launch
                    )
                )
            },
            allowedPaymentMethods = state.allowedPaymentMethods
        )
    }
    if (state.paymentCompleted) {
        PaymentCompletedSheet(
            therapistProfileUri = therapistProfileUri,
            therapistDisplayName = therapistDisplayName,
            therapistSpecialization = therapistSpecialization,
            sessionDate = sessionDate,
            onDismiss = {
                onEvent(
                    GooglePaymentEvent.SetPaymentCompleted(false)
                )
            }
        )
    }
}