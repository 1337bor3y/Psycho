package com.example.psychoremastered.data.repository

import androidx.core.net.toUri
import com.example.psychoremastered.data.mappers.toClient
import com.example.psychoremastered.data.mappers.toClientDto
import com.example.psychoremastered.data.remote.ClientApi
import com.example.psychoremastered.data.remote.ImageStorageApi
import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val clientApi: ClientApi,
    private val storageApi: ImageStorageApi
) : ClientRepository {

    override suspend fun saveClient(client: Client) {
        val imageUri = storageApi.saveImage(client.avatarUri.toUri())
        clientApi.saveClient(
            client.toClientDto().copy(
                avatarUri = imageUri
            )
        )
    }

    override suspend fun removeClient(client: Client) {
       clientApi.removeClient(client.toClientDto())
    }

    override fun getClient(clientId: String): Flow<Client?> {
        return clientApi.getClient(clientId).map {
            it?.toClient()
        }
    }
}