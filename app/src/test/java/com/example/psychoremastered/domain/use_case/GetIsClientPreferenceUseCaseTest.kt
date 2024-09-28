package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.repository.PreferenceRepository
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetIsClientPreferenceUseCaseTest {

    private val preferenceRepository = mock<PreferenceRepository>()

    @After
    fun tearDown() {
        Mockito.reset(preferenceRepository)
    }

    @Test
    fun `Invoke, correct key, result boolean`() = runTest {
        val key = "correct key"
        Mockito.`when`(preferenceRepository.getBoolean(key)).thenReturn(true)

        val value = GetIsClientPreferenceUseCase(preferenceRepository).invoke(key)

        assertEquals(true, value)
    }

    @Test
    fun `Invoke, incorrect key, result boolean`() = runTest {
        val key = "incorrect key"
        Mockito.`when`(preferenceRepository.getBoolean(key)).thenReturn(null)

        val value = GetIsClientPreferenceUseCase(preferenceRepository).invoke(key)

        assertEquals(null, value)
    }
}