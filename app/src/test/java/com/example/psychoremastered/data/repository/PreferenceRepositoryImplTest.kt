package com.example.psychoremastered.data.repository

import com.example.psychoremastered.data.local.preference.PreferenceManager
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class PreferenceRepositoryImplTest {

    private val preferenceManager = mock<PreferenceManager>()

    @After
    fun tearDown() {
        Mockito.reset(preferenceManager)
    }

    @Test
    fun `Get boolean, correct key, result boolean`() = runTest {
        val key = "correct key"
        Mockito.`when`(preferenceManager.getBoolean(key)).thenReturn(true)

        val value = PreferenceRepositoryImpl(preferenceManager).getBoolean(key)

        assertEquals(true, value)
    }

    @Test
    fun `Get boolean, incorrect key, result boolean`() = runTest {
        val key = "incorrect key"
        Mockito.`when`(preferenceManager.getBoolean(key)).thenReturn(null)

        val value = PreferenceRepositoryImpl(preferenceManager).getBoolean(key)

        assertEquals(null, value)
    }
}