package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.pay.PaymentsUtil
import com.example.psychoremastered.domain.repository.PaymentsRepository
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.PaymentData
import org.json.JSONArray
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
    private val paymentsUtil: PaymentsUtil
): PaymentsRepository {

    override fun getAllowedPaymentMethods(): JSONArray {
       return paymentsUtil.getAllowedPaymentMethods()
    }

    override suspend fun isReadyToPay(): Boolean {
        return paymentsUtil.isReadyToPayRequest()
    }

    override fun getLoadPaymentDataTask(price: String): Task<PaymentData> {
        return paymentsUtil.getLoadPaymentDataTask(price)
    }
}