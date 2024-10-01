package com.example.psychoremastered.presentation.client

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.psychoremastered.core.screen_route.ClientScreenRoutes
import com.example.psychoremastered.presentation.client.chat.ClientChatsScreen
import com.example.psychoremastered.presentation.client.model.BottomNavigationItem
import com.example.psychoremastered.presentation.client.proflie.ClientProfileScreen
import com.example.psychoremastered.presentation.client.therapist_list.TherapistListScreen
import com.example.psychoremstered.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientUI(
    navController: NavController
) {
    val clientNavController = rememberNavController()
    var title by rememberSaveable {
        mutableStateOf("Therapist list")
    }
    var navigationSelectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { padding ->
            NavHost(
                navController = clientNavController,
                startDestination = ClientScreenRoutes.TherapistListScreen,
                modifier = Modifier.padding(padding)
            ) {
                composable<ClientScreenRoutes.TherapistListScreen> {
                    TherapistListScreen()
                }
                composable<ClientScreenRoutes.ProfileScreen> {
                    ClientProfileScreen()
                }
                composable<ClientScreenRoutes.ChatsScreen> {
                    ClientChatsScreen()
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.white)
            ) {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navigationSelectedItem = index
                                clientNavController.navigate(navigationItem.route) {
                                    popUpTo(clientNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                    title = navigationItem.label
                                }
                            }
                        )
                    }
            }
        }
    )
}