package com.example.psychoremastered.data.remote

import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.data.remote.dto.ClientDto
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientFirebaseDataSource @Inject constructor(
    db: FirebaseDatabase
) : ClientApi {
    private val reference = db.getReference(Constants.FIREBASE_DB_CLIENT_PATH)

    override suspend fun saveClient(client: ClientDto) {
        reference.child(client.id).setValue(client).await()
    }

    override suspend fun removeClient(client: ClientDto) {
        reference.child(client.id).removeValue().await()
    }

    override fun getClient(clientId: String): Flow<ClientDto?> {
        return reference.child(clientId).snapshots.map {
            it.getValue<ClientDto>()
        }
    }
}