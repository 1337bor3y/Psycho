package com.example.psychoremastered.presentation.client

import androidx.navigation.NavController

sealed interface ClientEvent {
    data class SignOut(val navController: NavController) : ClientEvent
    data class ShowFavouriteTherapists(val show: Boolean) : ClientEvent
}