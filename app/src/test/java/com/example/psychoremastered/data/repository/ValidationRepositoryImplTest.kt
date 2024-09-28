package com.example.psychoremastered.data.repository

import app.cash.turbine.test
import com.example.psychoremastered.data.validation.PatternValidator
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ValidationRepositoryImplTest {

    private val patterValidator = mock<PatternValidator>()

    @After
    fun tearDown() {
        Mockito.reset(patterValidator)
    }

    @Test
    fun `Validate profile image, profile image is valid, return flow with true`() = runTest {
        val img = "valid profile image"
        val actualFlow = ValidationRepositoryImpl(patterValidator).validateProfileImage(img)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(true, actual)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate profile image, profile image is blank, return flow with false and error`() =
        runTest {
            val img = ""
            val actualFlow = ValidationRepositoryImpl(patterValidator).validateProfileImage(img)

            actualFlow.test {
                val actual = awaitItem().data
                assertEquals(false, actual)
                val error = awaitItem().errorMessage
                assert(error != null)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Validate first name, first name is valid, return flow with true`() = runTest {
        val firstName = "valid first name"
        val actualFlow = ValidationRepositoryImpl(patterValidator).validateFirstName(firstName)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(true, actual)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate first name, first name is blank, return flow with false and error`() = runTest {
        val firstName = ""
        val actualFlow = ValidationRepositoryImpl(patterValidator).validateFirstName(firstName)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(false, actual)
            val error = awaitItem().errorMessage
            assert(error != null)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate surname, surname is valid, return flow with true`() = runTest {
        val surname = "valid surname"
        val actualFlow = ValidationRepositoryImpl(patterValidator).validateSurname(surname)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(true, actual)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate surname, surname is blank, return flow with false and error`() = runTest {
        val surname = ""
        val actualFlow = ValidationRepositoryImpl(patterValidator).validateSurname(surname)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(false, actual)
            val error = awaitItem().errorMessage
            assert(error != null)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate email, email is valid, return flow with true`() = runTest {
        val email = "valid email"
        Mockito.`when`(patterValidator.matches(email)).thenReturn(true)

        val actualFlow = ValidationRepositoryImpl(patterValidator).validateEmail(email)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(true, actual)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate email, email is not valid, return flow with false and error`() = runTest {
        val email = "invalid email"
        Mockito.`when`(patterValidator.matches(email)).thenReturn(false)

        val actualFlow = ValidationRepositoryImpl(patterValidator).validateEmail(email)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(false, actual)
            val error = awaitItem().errorMessage
            assert(error != null)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate email, email is blank, return flow with false and error`() = runTest {
        val email = ""
        val actualFlow = ValidationRepositoryImpl(patterValidator).validateEmail(email)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(false, actual)
            val error = awaitItem().errorMessage
            assert(error != null)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate password, password is valid, return flow with true`() = runTest {
        val password = "12345678"

        val actualFlow = ValidationRepositoryImpl(patterValidator).validatePassword(password)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(true, actual)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate password, length less than 8, return flow with false and error`() = runTest {
        val password = "1234567"

        val actualFlow = ValidationRepositoryImpl(patterValidator).validatePassword(password)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(false, actual)
            val error = awaitItem().errorMessage
            assert(error != null)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate password, password is blank, return flow with false and error`() = runTest {
        val password = ""
        val actualFlow = ValidationRepositoryImpl(patterValidator).validatePassword(password)

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(false, actual)
            val error = awaitItem().errorMessage
            assert(error != null)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate confirm password, passwords match, return flow with true`() = runTest {
        val password = "12345678"
        val confirmPassword = "12345678"

        val actualFlow = ValidationRepositoryImpl(patterValidator).validateConfirmPassword(
            password = password,
            confirmPassword = confirmPassword
        )

        actualFlow.test {
            val actual = awaitItem().data
            assertEquals(true, actual)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validate confirm password, passwords doesn't match, return flow with false and error`() =
        runTest {
            val password = "12345678"
            val confirmPassword = "1234567890"

            val actualFlow = ValidationRepositoryImpl(patterValidator).validateConfirmPassword(
                password = password,
                confirmPassword = confirmPassword
            )

            actualFlow.test {
                val actual = awaitItem().data
                assertEquals(false, actual)
                val error = awaitItem().errorMessage
                assert(error != null)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Validate confirm password, length less than 8, return flow with false and error`() =
        runTest {
            val password = "1234567"
            val confirmPassword = "1234567"

            val actualFlow = ValidationRepositoryImpl(patterValidator).validateConfirmPassword(
                password = password,
                confirmPassword = confirmPassword
            )

            actualFlow.test {
                val actual = awaitItem().data
                assertEquals(false, actual)
                val error = awaitItem().errorMessage
                assert(error != null)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Validate confirm password, password is blank, return flow with false and error`() =
        runTest {
            val password = ""
            val confirmPassword = ""

            val actualFlow = ValidationRepositoryImpl(patterValidator).validateConfirmPassword(
                password = password,
                confirmPassword = confirmPassword
            )

            actualFlow.test {
                val actual = awaitItem().data
                assertEquals(false, actual)
                val error = awaitItem().errorMessage
                assert(error != null)
                cancelAndIgnoreRemainingEvents()
            }
        }
}