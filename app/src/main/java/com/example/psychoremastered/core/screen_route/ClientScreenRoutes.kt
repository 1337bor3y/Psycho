package com.example.psychoremastered.core.screen_route

import kotlinx.serialization.Serializable

@Serializable
sealed class ClientScreenRoutes {

    @Serializable
    data object TherapistListScreen: ClientScreenRoutes()

    @Serializable
    data object ProfileScreen: ClientScreenRoutes()

    @Serializable
    data object ChatsScreen: ClientScreenRoutes()
}