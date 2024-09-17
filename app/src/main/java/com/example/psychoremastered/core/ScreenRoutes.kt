package com.example.psychoremastered.core

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoutes {

    @Serializable
    data object ChooseScreen: ScreenRoutes()

    @Serializable
    data object PasswordAuthScreen: ScreenRoutes()

    @Serializable
    data object TherapistRegistrationScreen: ScreenRoutes()
}