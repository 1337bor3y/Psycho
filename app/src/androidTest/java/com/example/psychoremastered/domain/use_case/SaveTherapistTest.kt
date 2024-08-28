package com.example.psychoremastered.domain.use_case

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.psychoremastered.data.local.TherapistDao
import com.example.psychoremastered.data.local.TherapistDatabase
import com.example.psychoremastered.data.mappers.toTherapist
import com.example.psychoremastered.data.mappers.toTherapistEntity
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.Therapist
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SaveTherapistTest {
    private lateinit var therapistDao: TherapistDao
    private lateinit var therapistDatabase: TherapistDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        therapistDatabase = Room.inMemoryDatabaseBuilder(
            context,
            TherapistDatabase::class.java
        ).build()
        therapistDao = therapistDatabase.therapistDao
    }

    @After
    fun tearDown() {
        therapistDatabase.close()
    }

    @Test
    fun insertTherapist_therapistInDatabase() = runBlocking {
        val expectedTherapist = Therapist(
            id = 0,
            specializations = listOf("specialization1", "specialization2", "specialization3"),
            workFields = listOf("workField1", "workField2", "workField3"),
            languages = listOf("language1", "language2"),
            description = "description",
            price = "123",
            degrees = listOf(
                Degree(
                    id = 0,
                    university = "university",
                    speciality = "speciality",
                    admissionYear = "2016",
                    graduationYear = "2022",
                    documentImage = "documentImage"
                )
            )
        )

        therapistDao.upsertTherapist(expectedTherapist.toTherapistEntity())

        val actualTherapist = therapistDao.getTherapist(0).toTherapist()

        assertEquals(expectedTherapist, actualTherapist)
    }

    @Test
    fun updateTherapist_therapistIsUpdated() = runBlocking {
        val existingTherapist = Therapist(
            id = 0,
            specializations = listOf("specialization1", "specialization2", "specialization3"),
            workFields = listOf("workField1", "workField2", "workField3"),
            languages = listOf("language1", "language2"),
            description = "description",
            price = "123",
            degrees = listOf(
                Degree(
                    id = 0,
                    university = "university",
                    speciality = "speciality",
                    admissionYear = "2016",
                    graduationYear = "2022",
                    documentImage = "documentImage"
                )
            )
        )
        therapistDao.upsertTherapist(existingTherapist.toTherapistEntity())

        val updatedTherapist = existingTherapist.copy(
            price = "3333"
        )
        therapistDao.upsertTherapist(updatedTherapist.toTherapistEntity())

        val actualTherapist = therapistDao.getTherapist(0).toTherapist()

        assertEquals(updatedTherapist, actualTherapist)
    }
}