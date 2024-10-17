package com.example.psychoremastered.presentation.google_pay

data class GooglePaymentState (
    val notStarted: Boolean = true,
    val available: Boolean = false,
    val paymentCompleted: Boolean = false,
    val error: String? = null,
    val allowedPaymentMethods: String = ""
)