package com.example.psychoremastered.domain.use_case

import app.cash.turbine.test
import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.io.IOException

class CreateUserWithEmailAndPasswordUseCaseTest {

    private val authRepository = mock<AuthRepository>()

    @After
    fun tearDown() {
        Mockito.reset(authRepository)
    }

    @Test
    fun `Invoke, result user`() = runTest {
        val email = "email"
        val password = "password"
        val expectedUser = User(
            userId = "123",
            displayName = "Name",
            email = "email@gmail.com",
            profilePictureUri = "uri"
        )
        Mockito.`when`(authRepository.createUserWithEmailAndPassword(
            authEmail = email,
            authPassword = password
        )).thenReturn(expectedUser)

        val actualFlow = CreateUserWithEmailAndPasswordUseCase(authRepository).invoke(
            authEmail = email,
            authPassword = password
        )

        actualFlow.test {
            awaitItem()
            val actual = awaitItem().data
            assertEquals(expectedUser, actual)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Invoke with Exception, result exception handled with message`() = runTest {
        val email = "email"
        val password = "password"
        Mockito.`when`(authRepository.createUserWithEmailAndPassword(
            authEmail = email,
            authPassword = password
        )).thenAnswer {
            throw Exception()
        }

        val actualFlow = CreateUserWithEmailAndPasswordUseCase(authRepository).invoke(
            authEmail = email,
            authPassword = password
        )

        actualFlow.test {
            awaitItem()
            val expectedErrorMessage = Exception().localizedMessage ?: "An unexpected error occurred"
            val actual = awaitItem().errorMessage
            assertEquals(expectedErrorMessage, actual)
            cancelAndIgnoreRemainingEvents()
        }
    }
}