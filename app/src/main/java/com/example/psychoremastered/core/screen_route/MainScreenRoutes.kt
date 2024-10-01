package com.example.psychoremastered.core.screen_route

import kotlinx.serialization.Serializable

@Serializable
sealed class MainScreenRoutes {

    @Serializable
    data object ChooseScreen: MainScreenRoutes()

    @Serializable
    data object PasswordAuthScreen: MainScreenRoutes()

    @Serializable
    data class TherapistRegistrationScreen(
        val userId: String,
        val email: String?,
        val displayName: String?,
        val profilePictureUri: String?
    ): MainScreenRoutes()

    @Serializable
    data object ClientScreen: MainScreenRoutes()
}