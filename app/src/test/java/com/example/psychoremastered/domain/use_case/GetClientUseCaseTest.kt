package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.repository.ClientRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetClientUseCaseTest {

    private val clientRepository = mock<ClientRepository>()

    @After
    fun tearDown() {
        Mockito.reset(clientRepository)
    }

    @Test
    fun `Invoke, correct client id, result flow with client`() = runTest {
        val clientId = "correct id"
        val flowClient = flow<Client?> {
            emit(
                Client(
                    id = "123",
                    displayName = "Name",
                    email = "email@gmail.com",
                    avatarUri = "uri"
                )
            )
        }
        Mockito.`when`(clientRepository.getClient(clientId)).thenReturn(flowClient)

        val expectedClient = flowClient.firstOrNull()
        val client = GetClientUseCase(clientRepository).invoke(clientId).firstOrNull()

        assertEquals(expectedClient, client)
    }

    @Test
    fun `Invoke, incorrect client id, result flow with null`() = runTest {
        val clientId = "incorrect id"
        val flowClient = flow<Client?> {
            emit(null)
        }
        Mockito.`when`(clientRepository.getClient(clientId)).thenReturn(flowClient)

        val client = GetClientUseCase(clientRepository).invoke(clientId).firstOrNull()

        assertEquals(flowClient.firstOrNull(), client)
    }
}