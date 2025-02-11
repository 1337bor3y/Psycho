package com.example.psychoremastered.presentation.therapist_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.domain.use_case.GetFavouriteTherapistsUseCase
import com.example.psychoremastered.domain.use_case.GetPagingTherapistsUseCase
import com.example.psychoremastered.domain.use_case.RemoveFavouriteTherapistUseCase
import com.example.psychoremastered.domain.use_case.SaveFavouriteTherapistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TherapistListViewModel @Inject constructor(
    private val getPagingTherapistsUseCase: GetPagingTherapistsUseCase,
    private val saveFavouriteTherapistUseCase: SaveFavouriteTherapistUseCase,
    private val getFavouriteTherapistsUseCase: GetFavouriteTherapistsUseCase,
    private val removeFavouriteTherapistUseCase: RemoveFavouriteTherapistUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TherapistListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    therapists = getPagingTherapistsUseCase().cachedIn(viewModelScope),
                    favouriteTherapists = getFavouriteTherapistsUseCase().first()
                )
            }
        }
    }

    fun onEvent(event: TherapistListEvent) {
        when (event) {
            is TherapistListEvent.RemoveFavouriteTherapist -> removeFavouriteTherapist(
                event.therapist,
                event.updateFavouriteTherapistList
            )

            is TherapistListEvent.SaveFavouriteTherapist -> saveFavouriteTherapist(event.therapist)

            TherapistListEvent.ShowFavouriteTherapists -> showFavouriteTherapists()
        }
    }

    private fun removeFavouriteTherapist(
        therapist: Therapist,
        updateFavouriteTherapistList: Boolean
    ) {
        viewModelScope.launch {
            removeFavouriteTherapistUseCase(therapist)
            if (updateFavouriteTherapistList) {
                _state.update {
                    it.copy(
                        favouriteTherapists = getFavouriteTherapistsUseCase().first()
                    )
                }
            }
        }
    }

    private fun saveFavouriteTherapist(therapist: Therapist) {
        viewModelScope.launch {
            saveFavouriteTherapistUseCase(therapist)
        }
    }

    private fun showFavouriteTherapists() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    favouriteTherapists = getFavouriteTherapistsUseCase().first()
                )
            }
        }
    }
}