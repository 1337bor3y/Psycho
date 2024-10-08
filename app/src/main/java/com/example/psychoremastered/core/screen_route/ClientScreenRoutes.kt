package com.example.psychoremastered.core.screen_route

import com.example.psychoremastered.domain.model.Therapist
import kotlinx.serialization.Serializable

@Serializable
sealed class ClientScreenRoutes {

    @Serializable
    data object TherapistListScreen: ClientScreenRoutes()

    @Serializable
    data class PreviewTherapistScreen(val therapist: Therapist): ClientScreenRoutes()

    @Serializable
    data object ProfileScreen: ClientScreenRoutes()

    @Serializable
    data object ChatsScreen: ClientScreenRoutes()
}