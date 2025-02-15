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
    onCompletePayment: () -> Unit,
    paymentCompletedSheet: @Composable (setPaymentCompleted: () -> Unit) -> Unit
) {
    val state by googlePaymentViewModel.state.collectAsStateWithLifecycle()
    val onEvent = googlePaymentViewModel::onEvent
    val context = LocalContext.current
    val paymentDataLauncher = rememberLauncherForActivityResult(
        contract = TaskResultContracts.GetPaymentDataResult()
    ) { taskResult ->
        when (taskResult.status.statusCode) {
            CommonStatusCodes.SUCCESS -> {
                taskResult.result?.let { paymentData ->
                    onEvent(
                        GooglePaymentEvent.SetPaymentData(paymentData, onCompletePayment)
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
        paymentCompletedSheet {
            onEvent(
                GooglePaymentEvent.SetPaymentCompleted(false)
            )
        }
    }
}