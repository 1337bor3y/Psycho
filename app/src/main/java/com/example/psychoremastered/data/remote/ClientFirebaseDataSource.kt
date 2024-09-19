package com.example.psychoremastered.data.remote

import com.example.psychoremastered.data.remote.dto.ClientDto
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ClientFirebaseDataSource @Inject constructor(
    db: FirebaseDatabase
) : ClientApi {
    private val reference = db.getReference("clients")

    override suspend fun saveClient(client: ClientDto): Boolean {
        return reference.child(client.id).setValue(client).isSuccessful
    }

    override suspend fun removeClient(client: ClientDto): Boolean {
        return reference.child(client.id).removeValue().isSuccessful
    }

    override suspend fun getClient(clientId: String): Flow<ClientDto> {
        return reference.child(clientId).snapshots.mapNotNull {
            it.getValue<ClientDto>()
        }
    }
}