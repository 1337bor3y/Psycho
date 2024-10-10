package com.example.psychoremastered.presentation.client.therapist_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.psychoremastered.domain.use_case.GetPagingTherapistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TherapistListViewModel @Inject constructor(
    private val getPagingTherapistsUseCase: GetPagingTherapistsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TherapistListState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                therapists = getPagingTherapistsUseCase().cachedIn(viewModelScope)
            )
        }
    }
}