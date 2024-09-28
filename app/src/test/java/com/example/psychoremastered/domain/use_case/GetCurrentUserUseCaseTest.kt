package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.domain.repository.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCurrentUserUseCaseTest {

    private val authRepository = mock<AuthRepository>()

    @After
    fun tearDown() {
        Mockito.reset(authRepository)
    }

    @Test
    fun `Invoke, user exists, result user`() = runTest {
        val expectedUser = User(
            userId = "123",
            displayName = "Name",
            email = "email@gmail.com",
            profilePictureUri = "uri"
        )
        Mockito.`when`(authRepository.getCurrentUser())
            .thenReturn(expectedUser)

        val user = GetCurrentUserUseCase(authRepository).invoke()

        assertEquals(expectedUser, user)
    }

    @Test
    fun `Invoke, user does not exist, result null`() = runTest {
        Mockito.`when`(authRepository.getCurrentUser())
            .thenReturn(null)

        val user = GetCurrentUserUseCase(authRepository).invoke()

        assertEquals(null, user)
    }
}