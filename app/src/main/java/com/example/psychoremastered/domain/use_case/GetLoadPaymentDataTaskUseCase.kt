package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.PaymentsRepository
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.PaymentData
import javax.inject.Inject

class GetLoadPaymentDataTaskUseCase @Inject constructor(
    private val paymentsRepository: PaymentsRepository
) {
    operator fun invoke(price: String): Task<PaymentData> =
        paymentsRepository.getLoadPaymentDataTask(price)
}