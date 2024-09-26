package com.example.psychoremastered.core

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoutes {

    @Serializable
    data object ChooseScreen: ScreenRoutes()

    @Serializable
    data object PasswordAuthScreen: ScreenRoutes()

    @Serializable
    data class TherapistRegistrationScreen(
        val userId: String,
        val email: String?,
        val displayName: String?,
        val profilePictureUri: String?
    ): ScreenRoutes()
}