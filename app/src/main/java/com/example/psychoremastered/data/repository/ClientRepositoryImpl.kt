package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.mappers.toClient
import com.example.psychoremastered.data.mappers.toClientDto
import com.example.psychoremastered.data.remote.ClientApi
import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val clientApi: ClientApi
) : ClientRepository {

    override suspend fun saveClient(client: Client): Boolean {
        return clientApi.saveClient(client.toClientDto())
    }

    override suspend fun removeClient(client: Client): Boolean {
        return clientApi.removeClient(client.toClientDto())
    }

    override suspend fun getClient(clientId: String): Flow<Client> {
        return clientApi.getClient(clientId).map {
            it.toClient()
        }
    }
}