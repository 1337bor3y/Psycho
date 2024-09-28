package com.example.psychoremastered.domain.use_case

import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.repository.TherapistRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetTherapistUseCaseTest {

    private val therapistRepository = mock<TherapistRepository>()

    @After
    fun tearDown() {
        Mockito.reset(therapistRepository)
    }

    @Test
    fun `Invoke, correct therapist id, result flow with therapist`() = runTest {
        val therapistId = "correct id"
        val flowTherapist = flow<Therapist?> {
            emit(
                Therapist(
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
                        Degree(
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
        Mockito.`when`(therapistRepository.getTherapist(therapistId)).thenReturn(flowTherapist)

        val expectedTherapist = flowTherapist.firstOrNull()
        val therapist = GetTherapistUseCase(therapistRepository).invoke(therapistId).firstOrNull()

        assertEquals(expectedTherapist, therapist)
    }

    @Test
    fun `Invoke, incorrect therapist id, result flow with null`() = runTest {
        val therapistId = "incorrect id"
        val flowTherapist = flow<Therapist?> {
            emit(null)
        }
        Mockito.`when`(therapistRepository.getTherapist(therapistId)).thenReturn(flowTherapist)

        val client = GetTherapistUseCase(therapistRepository).invoke(therapistId).firstOrNull()

        assertEquals(flowTherapist.firstOrNull(), client)
    }
}