package com.example.psychoremastered.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.psychoremastered.core.util.Constants
import com.example.psychoremastered.data.remote.dto.TherapistDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TherapistsPagingSource @Inject constructor(
    private val db: FirebaseDatabase
) : PagingSource<DataSnapshot, TherapistDto>() {
    override fun getRefreshKey(state: PagingState<DataSnapshot, TherapistDto>): DataSnapshot? = null

    override suspend fun load(params: LoadParams<DataSnapshot>) = try {
        val queryTherapists = db.getReference(Constants.FIREBASE_DB_THERAPIST_PATH)
            .orderByKey().limitToFirst(Constants.PAGE_SIZE)
        val currentPage = params.key ?: queryTherapists.get().await()
        val lastVisibleProductKey = currentPage.children.last().key
        val nextPage = queryTherapists.startAfter(lastVisibleProductKey).get().await()
        val therapists = currentPage.children.mapNotNull {
            it.getValue<TherapistDto>()
        }
        LoadResult.Page(
            data = therapists,
            prevKey = null,
            nextKey = nextPage
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}