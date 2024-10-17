package com.example.psychoremastered.domain.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.PaymentData
import org.json.JSONArray

interface PaymentsRepository {

    fun getAllowedPaymentMethods(): JSONArray

    suspend fun isReadyToPay(): Boolean

    fun getLoadPaymentDataTask(price: String): Task<PaymentData>
}