package com.example.psychoremastered.core.screen_route

import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
sealed class MainScreenRoutes {

    @Serializable
    data object ChooseScreen: MainScreenRoutes()

    @Serializable
    data object PasswordAuthScreen: MainScreenRoutes()

    @Serializable
    data class TherapistRegistrationScreen(val user: User): MainScreenRoutes()

    @Serializable
    data object ClientScreen: MainScreenRoutes()
}