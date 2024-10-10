package com.example.psychoremastered.data.remote

import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.data.remote.dto.TherapistDto
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TherapistFirebaseDataSource @Inject constructor(
    db: FirebaseDatabase
): TherapistApi {
    private val reference = db.getReference(Constants.FIREBASE_DB_THERAPIST_PATH)

    override suspend fun saveTherapist(therapist: TherapistDto) {
        reference.child(therapist.id).setValue(therapist).await()
    }

    override suspend fun removeTherapist(therapist: TherapistDto) {
        reference.child(therapist.id).removeValue().await()
    }

    override fun getTherapist(therapistId: String): Flow<TherapistDto?> {
        return reference.child(therapistId).snapshots.map {
            it.getValue<TherapistDto>()
        }
    }
}