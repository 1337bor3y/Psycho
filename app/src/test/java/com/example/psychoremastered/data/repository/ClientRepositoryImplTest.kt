package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.mappers.toClient
import com.example.psychoremastered.data.remote.ClientApi
import com.example.psychoremastered.data.remote.ImageStorageApi
import com.example.psychoremastered.data.remote.dto.ClientDto
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ClientRepositoryImplTest {

    private val clientApi = mock<ClientApi>()
    private val storageApi = mock<ImageStorageApi>()

    @After
    fun tearDown() {
        Mockito.reset(clientApi, storageApi)
    }

    @Test
    fun `Get client, correct client id, result flow with client`() = runTest {
        val clientId = "correct id"
        val flowClientDto = flow<ClientDto?> {
            emit(
                ClientDto(
                    id = "123",
                    displayName = "Name",
                    email = "email@gmail.com",
                    avatarUri = "uri"
                )
            )
        }
        Mockito.`when`(clientApi.getClient(clientId)).thenReturn(flowClientDto)

        val expectedClient = flowClientDto.firstOrNull()?.toClient()
        val client = ClientRepositoryImpl(clientApi, storageApi).getClient(clientId).firstOrNull()

        assertEquals(expectedClient, client)
    }

    @Test
    fun `Get client, incorrect client id, result flow with null`() = runTest {
        val clientId = "incorrect id"
        val flowClientDto = flow<ClientDto?> {
            emit(null)
        }
        Mockito.`when`(clientApi.getClient(clientId)).thenReturn(flowClientDto)

        val client = ClientRepositoryImpl(clientApi, storageApi).getClient(clientId).firstOrNull()

        assertEquals(flowClientDto.firstOrNull(), client)
    }
}