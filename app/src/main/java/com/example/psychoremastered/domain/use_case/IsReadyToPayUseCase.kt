package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.PaymentsRepository
import javax.inject.Inject

class IsReadyToPayUseCase @Inject constructor(
    private val paymentsRepository: PaymentsRepository
) {
    suspend operator fun invoke(): Boolean =
        paymentsRepository.isReadyToPay()
}