package com.example.psychoremastered.data.remote

import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.data.remote.dto.UnavailableTimeDto
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UnavailableTimeFirebaseSource @Inject constructor(
    db: FirebaseDatabase
): UnavailableTimeApi {
    private val reference = db.getReference(Constants.FIREBASE_DB_UNAVAILABLE_TIME_PATH)

    override suspend fun saveUnavailableTime(unavailableTime: UnavailableTimeDto) {
        reference.child(unavailableTime.therapistId).child(unavailableTime.date)
            .setValue(unavailableTime.unavailableTimes).await()
    }

    override suspend fun removeUnavailableTime(unavailableTime: UnavailableTimeDto) {
        reference.child(unavailableTime.therapistId).child(unavailableTime.date)
            .removeValue().await()
    }

    override fun getUnavailableTime(therapistId: String, date: String): Flow<List<String>> {
        return reference.child(therapistId).child(date).snapshots.mapNotNull {
            it.getValue<List<String>>()
        }
    }
}