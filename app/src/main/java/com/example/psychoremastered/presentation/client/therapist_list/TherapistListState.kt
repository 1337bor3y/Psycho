package com.example.psychoremastered.presentation.client.therapist_list

import androidx.paging.PagingData
import com.example.psychoremastered.domain.model.Therapist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class TherapistListState(
    val therapists: Flow<PagingData<Therapist>> = emptyFlow()
)