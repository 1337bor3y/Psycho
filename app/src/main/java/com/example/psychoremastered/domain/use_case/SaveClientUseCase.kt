package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.repository.ClientRepository
import javax.inject.Inject

class SaveClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository
) {
    suspend operator fun invoke(client: Client) {
        clientRepository.saveClient(client)
    }
}