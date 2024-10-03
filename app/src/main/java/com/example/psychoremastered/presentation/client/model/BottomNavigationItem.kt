package com.example.psychoremastered.presentation.client.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.psychoremastered.core.screen_route.ClientScreenRoutes

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Search,
    val route : ClientScreenRoutes = ClientScreenRoutes.TherapistListScreen
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Therapist list",
                icon = Icons.Filled.Search,
                route = ClientScreenRoutes.TherapistListScreen
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = Icons.Filled.Person,
                route = ClientScreenRoutes.ProfileScreen
            ),
            BottomNavigationItem(
                label = "Chats",
                icon = Icons.AutoMirrored.Filled.Chat,
                route = ClientScreenRoutes.ChatsScreen
            ),
        )
    }
}