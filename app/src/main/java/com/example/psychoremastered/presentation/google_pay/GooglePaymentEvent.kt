package com.example.psychoremastered.presentation.google_pay

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.wallet.PaymentData

sealed interface GooglePaymentEvent {
    data class SetPaymentData(val paymentData: PaymentData) : GooglePaymentEvent
    data class LoadPaymentData(
        val price: String,
        val onCompleteListener: OnCompleteListener<PaymentData>
    ) : GooglePaymentEvent
    data class SetPaymentCompleted(val isCompleted: Boolean) : GooglePaymentEvent
}