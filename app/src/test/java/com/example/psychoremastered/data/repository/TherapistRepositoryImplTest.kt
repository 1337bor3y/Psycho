package com.example.psychoremastered.data.repository

import androidx.paging.PagingConfig
import com.example.psychoremastered.data.auth.AuthApi
import com.example.psychoremastered.data.mappers.toTherapist
import com.example.psychoremastered.data.remote.ImageStorageApi
import com.example.psychoremastered.data.remote.TherapistApi
import com.example.psychoremastered.data.remote.TherapistsPagingSource
import com.example.psychoremastered.data.remote.dto.DegreeDto
import com.example.psychoremastered.data.remote.dto.TherapistDto
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class TherapistRepositoryImplTest {
    private val therapistApi = mock<TherapistApi>()
    private val storageApi = mock<ImageStorageApi>()
    private val authApi = mock<AuthApi>()
    private val source = mock<TherapistsPagingSource>()
    private val config = mock<PagingConfig>()

    @After
    fun tearDown() {
        Mockito.reset(therapistApi, storageApi)
    }

    @Test
    fun `Get therapist, correct therapist id, result flow with therapist`() = runTest {
        val therapistId = "correct id"
        val flowTherapistDto = flow<TherapistDto?> {
            emit(
                TherapistDto(
                    id = "123",
                    displayName = "Name",
                    email = "email@gmail.com",
                    avatarUri = "uri",
                    specializations = listOf("qeqew", "Qeqe"),
                    workFields = listOf("qeqew", "Qeqe"),
                    languages = listOf("qeqew", "Qeqe"),
                    description = "Adads",
                    price = "123",
                    hasDegree = true,
                    degrees = listOf(
                        DegreeDto(
                            id = 1,
                            university = "Kpi",
                            speciality = "Tef",
                            admissionYear = "1231",
                            graduationYear = "1313",
                            documentImage = "uri"
                        )
                    )
                )
            )
        }
        Mockito.`when`(therapistApi.getTherapist(therapistId)).thenReturn(flowTherapistDto)

        val expectedTherapist = flowTherapistDto.firstOrNull()?.toTherapist()
        val therapist = TherapistRepositoryImpl(therapistApi, storageApi, authApi, source, config)
            .getTherapist(therapistId).firstOrNull()

        assertEquals(expectedTherapist, therapist)
    }

    @Test
    fun `Get therapist, incorrect therapist id, result flow with null`() = runTest {
        val therapistId = "incorrect id"
        val flowTherapistDto = flow<TherapistDto?> {
            emit(null)
        }
        Mockito.`when`(therapistApi.getTherapist(therapistId)).thenReturn(flowTherapistDto)

        val therapist = TherapistRepositoryImpl(therapistApi, storageApi, authApi, source, config)
            .getTherapist(therapistId).firstOrNull()

        assertEquals(flowTherapistDto.firstOrNull(), therapist)
    }
}