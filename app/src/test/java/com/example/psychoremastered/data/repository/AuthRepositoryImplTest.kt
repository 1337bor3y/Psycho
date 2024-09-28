package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.auth.AuthApi
import com.example.psychoremastered.data.auth.model.AuthUser
import com.example.psychoremastered.data.mappers.toUser
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class AuthRepositoryImplTest {

    private val authApi = mock<AuthApi>()

    @After
    fun tearDown() {
        Mockito.reset(authApi)
    }

    @Test
    fun `Sign in with credential, correct id token, result user`() = runTest {
        val idToken = "correct token"
        val expectedUser = AuthUser(
            userId = "123",
            displayName = "Name",
            email = "email@gmail.com",
            profilePictureUri = "uri"
        )
        Mockito.`when`(authApi.signInWithCredential(idToken)).thenReturn(expectedUser)

        val user = AuthRepositoryImpl(authApi).signInWithCredential(idToken)

        assertEquals(expectedUser.toUser(), user)
    }

    @Test
    fun `Sign in with credential, incorrect id token, result null`() = runTest {
        val idToken = "incorrect token"
        Mockito.`when`(authApi.signInWithCredential(idToken)).thenReturn(null)

        val user = AuthRepositoryImpl(authApi).signInWithCredential(idToken)

        assertEquals(null, user)
    }

    @Test
    fun `Create user with email and password, correct input data, result user`() = runTest {
        val email = "email@gmail.com"
        val password = "qaw123"
        val expectedUser = AuthUser(
            userId = "123",
            displayName = "Name",
            email = email,
            profilePictureUri = "uri"
        )
        Mockito.`when`(authApi.createUserWithEmailAndPassword(email, password))
            .thenReturn(expectedUser)

        val user =
            AuthRepositoryImpl(authApi).createUserWithEmailAndPassword(email, password)

        assertEquals(expectedUser.toUser(), user)
    }

    @Test
    fun `Create user with email and password, incorrect input data, result null`() = runTest {
        val email = "incorrect email@gmail.com"
        val password = "incorrect qaw123"
        Mockito.`when`(authApi.createUserWithEmailAndPassword(email, password))
            .thenReturn(null)

        val user =
            AuthRepositoryImpl(authApi).createUserWithEmailAndPassword(email, password)

        assertEquals(null, user)
    }

    @Test
    fun `Sign in with email and password, correct input data, result user`() = runTest {
        val email = "email@gmail.com"
        val password = "qaw123"
        val expectedUser = AuthUser(
            userId = "123",
            displayName = "Name",
            email = email,
            profilePictureUri = "uri"
        )
        Mockito.`when`(authApi.signInWithEmailAndPassword(email, password))
            .thenReturn(expectedUser)

        val user =
            AuthRepositoryImpl(authApi).signInWithEmailAndPassword(email, password)

        assertEquals(expectedUser.toUser(), user)
    }
    @Test
    fun `Sign in with email and password, incorrect input data, result null`() = runTest {
        val email = "incorrect email@gmail.com"
        val password = "incorrect qaw123"
        Mockito.`when`(authApi.signInWithEmailAndPassword(email, password))
            .thenReturn(null)

        val user =
            AuthRepositoryImpl(authApi).signInWithEmailAndPassword(email, password)

        assertEquals(null, user)
    }


    @Test
    fun `Get current user, user exists, result user`() = runTest {
        val expectedUser = AuthUser(
            userId = "123",
            displayName = "Name",
            email = "email@gmail.com",
            profilePictureUri = "uri"
        )
        Mockito.`when`(authApi.getCurrentUser())
            .thenReturn(expectedUser)

        val user = AuthRepositoryImpl(authApi).getCurrentUser()

        assertEquals(expectedUser.toUser(), user)
    }

    @Test
    fun `Get current user, user does not exist, result null`() = runTest {
        Mockito.`when`(authApi.getCurrentUser())
            .thenReturn(null)

        val user = AuthRepositoryImpl(authApi).getCurrentUser()

        assertEquals(null, user)
    }
}