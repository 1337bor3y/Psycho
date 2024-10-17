package com.example.psychoremastered.data.pay

import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.PaymentData
import org.json.JSONArray

interface PaymentsUtil {

    fun getAllowedPaymentMethods(): JSONArray

    suspend fun isReadyToPayRequest(): Boolean

    fun getLoadPaymentDataTask(price: String): Task<PaymentData>
}