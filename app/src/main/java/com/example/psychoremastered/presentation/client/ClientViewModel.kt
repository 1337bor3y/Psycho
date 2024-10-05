package com.example.psychoremastered.presentation.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.psychoremastered.core.screen_route.MainScreenRoutes
import com.example.psychoremastered.domain.use_case.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    fun onEvent(event: ClientEvent) {
        when (event) {
            is ClientEvent.SignOut -> signOut(event.navController)
        }
    }

    private fun signOut(navController: NavController) {
        viewModelScope.launch {
            signOutUseCase()
            navController.navigate(MainScreenRoutes.ChooseScreen)
        }
    }
}