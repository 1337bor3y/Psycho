package com.example.psychoremastered.data.remote

import com.example.psychoremastered.data.remote.dto.ClientDto
import kotlinx.coroutines.flow.Flow

interface ClientApi {

    suspend fun saveClient(client: ClientDto)

    suspend fun removeClient(client: ClientDto)

    fun getClient(clientId: String): Flow<ClientDto?>
}