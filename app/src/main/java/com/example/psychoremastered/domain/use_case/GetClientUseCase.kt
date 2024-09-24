package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClientUseCase @Inject constructor(
    private val clientRepository: ClientRepository
) {
    operator fun invoke(clientId: String): Flow<Client?> {
        return clientRepository.getClient(clientId = clientId)
    }
}