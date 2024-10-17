package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.PaymentsRepository
import javax.inject.Inject

class GetAllowedPaymentMethodsUseCase @Inject constructor(
    private val paymentsRepository: PaymentsRepository
) {
    operator fun invoke(): String = paymentsRepository.getAllowedPaymentMethods().toString()
}