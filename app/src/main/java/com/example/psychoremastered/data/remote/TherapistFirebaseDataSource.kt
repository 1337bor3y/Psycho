package com.example.psychoremastered.data.remote

import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.data.remote.dto.TherapistDto
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class TherapistFirebaseDataSource @Inject constructor(
    db: FirebaseDatabase
): TherapistApi {
    private val reference = db.getReference(Constants.FIREBASE_DB_THERAPIST_PATH)

    override suspend fun saveTherapist(therapist: TherapistDto): Boolean {
        return reference.child(therapist.id).setValue(therapist).isSuccessful
    }

    override suspend fun removeTherapist(therapist: TherapistDto): Boolean {
        return reference.child(therapist.id).removeValue().isSuccessful
    }

    override fun getTherapist(therapistId: String): Flow<TherapistDto> {
        return reference.child(therapistId).snapshots.mapNotNull {
            it.getValue<TherapistDto>()
        }
    }

    override fun getAllTherapists(): Flow<List<TherapistDto>> {
        return reference.snapshots.map { snapshot ->
            snapshot.children.mapNotNull {
                it.getValue<TherapistDto>()
            }
        }
    }
}