package com.example.psychoremastered.domain.repository

import com.example.psychoremastered.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    suspend fun saveClient(client: Client)

    suspend fun removeClient(client: Client)

    fun getClient(clientId: String): Flow<Client?>
}