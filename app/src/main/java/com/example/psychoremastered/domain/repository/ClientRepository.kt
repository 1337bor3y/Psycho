package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    suspend fun saveClient(client: Client): Boolean

    suspend fun removeClient(client: Client): Boolean

    suspend fun getClient(clientId: String): Flow<Client>
}