package com.example.psychoremastered.data.repository

import androidx.core.net.toUri
import com.example.psychoremastered.data.auth.AuthApi
import com.example.psychoremastered.data.mappers.toTherapist
import com.example.psychoremastered.data.mappers.toTherapistDto
import com.example.psychoremastered.data.remote.ImageStorageApi
import com.example.psychoremastered.data.remote.TherapistApi
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.repository.TherapistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class TherapistRepositoryImpl @Inject constructor(
    private val therapistApi: TherapistApi,
    private val storageApi: ImageStorageApi,
    private val authApi: AuthApi
) : TherapistRepository {

    override suspend fun saveTherapist(therapist: Therapist) {
        val imageUri = storageApi.saveImage(therapist.avatarUri.toUri())
        return therapistApi.saveTherapist(
            therapist.toTherapistDto().copy(
                avatarUri = imageUri
            )
        )
    }

    override suspend fun removeTherapist(therapist: Therapist) {
        return therapistApi.removeTherapist(therapist.toTherapistDto())
    }

    override fun getTherapist(therapistId: String): Flow<Therapist?> {
        return therapistApi.getTherapist(therapistId).map {
            it?.toTherapist()
        }
    }

    override fun getAllTherapists(): Flow<List<Therapist>> {
        return therapistApi.getAllTherapists().mapNotNull { therapist ->
            therapist
                .filter {
                    it.id != authApi.getCurrentUser()?.userId
                }
                .map {
                    it.toTherapist()
                }
        }
    }
}