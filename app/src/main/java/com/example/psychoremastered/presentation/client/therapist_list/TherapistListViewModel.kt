package com.example.psychoremastered.presentation.client.therapist_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.use_case.GetTherapistsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TherapistListViewModel @Inject constructor(
    private val getTherapistsListUseCase: GetTherapistsListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TherapistListState())
    val state = _state.asStateFlow()


    init {
        fetchTherapistsList ()
    }

    private fun fetchTherapistsList() {
        getTherapistsListUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> _state.update {
                    it.copy(
                        error = result.errorMessage ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }

                is Resource.Loading -> _state.update {
                    it.copy(
                        isLoading = true,
                        error = ""
                    )
                }

                is Resource.Success -> _state.update {
                    it.copy(
                        therapists = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}