package com.example.psychoremastered.presentation.client

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.psychoremastered.core.screen_route.ClientScreenRoutes
import com.example.psychoremastered.core.screen_route.parcelableType
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.presentation.client_chat.ClientChatsScreen
import com.example.psychoremastered.presentation.client.model.BottomNavigationItem
import com.example.psychoremastered.presentation.client_profile.ClientProfileScreen
import com.example.psychoremastered.presentation.therapist_list.PreviewTherapistScreen
import com.example.psychoremastered.presentation.therapist_list.TherapistListScreen
import com.example.psychoremastered.presentation.therapist_list.TherapistListViewModel
import com.example.psychoremstered.R
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientUI(
    onEvent: (ClientEvent) -> Unit,
    navController: NavController
) {
    val clientNavController = rememberNavController()
    var title by rememberSaveable {
        mutableStateOf("Therapists")
    }
    var navigationSelectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    val navItems = BottomNavigationItem().bottomNavigationItems()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = {
                    if (!clientNavController.popBackStack()) {
                        navController.popBackStack()
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    onEvent(
                        ClientEvent.SignOut(navController)
                    )
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Sing out"
                    )
                }
            }
        )
        Box(modifier = Modifier.weight(1f)) {
            NavHost(
                navController = clientNavController,
                startDestination = ClientScreenRoutes.TherapistListScreen
            ) {
                composable<ClientScreenRoutes.TherapistListScreen> {
                    navigationSelectedItem = 0
                    title = navItems[navigationSelectedItem].label
                    val listViewModel = hiltViewModel<TherapistListViewModel>()
                    val listState by listViewModel.state.collectAsStateWithLifecycle()
                    TherapistListScreen(
                        state = listState,
                        onEvent = listViewModel::onEvent,
                        navController = clientNavController
                    )
                }
                composable<ClientScreenRoutes.ProfileScreen> {
                    navigationSelectedItem = 1
                    title = navItems[navigationSelectedItem].label
                    ClientProfileScreen()
                }
                composable<ClientScreenRoutes.ChatsScreen> {
                    navigationSelectedItem = 2
                    title = navItems[navigationSelectedItem].label
                    ClientChatsScreen()
                }
                composable<ClientScreenRoutes.PreviewTherapistScreen>(
                    typeMap = mapOf(
                        typeOf<Therapist>() to parcelableType<Therapist>(),
                        typeOf<Degree>() to parcelableType<Degree>()
                    )
                ) {
                    navigationSelectedItem = 0
                    val therapist =
                        it.toRoute<ClientScreenRoutes.PreviewTherapistScreen>().therapist
                    title = therapist.displayName
                    PreviewTherapistScreen(therapist = therapist)
                }
            }
        }
        NavigationBar(
            containerColor = colorResource(id = R.color.white)
        ) {
            navItems.forEachIndexed { index, navigationItem ->
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
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}