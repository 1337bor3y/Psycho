package com.example.psychoremastered.presentation.client

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.psychoremastered.core.screen_route.ClientScreenRoutes
import com.example.psychoremastered.core.screen_route.parcelableType
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.domain.model.Therapist
import com.example.psychoremastered.presentation.client.model.BottomNavigationItem
import com.example.psychoremastered.presentation.client_chat.ClientChatsScreen
import com.example.psychoremastered.presentation.client_profile.ClientProfileScreen
import com.example.psychoremastered.presentation.therapist_list.PreviewTherapistScreen
import com.example.psychoremastered.presentation.therapist_list.TherapistListEvent
import com.example.psychoremastered.presentation.therapist_list.TherapistListScreen
import com.example.psychoremastered.presentation.therapist_list.TherapistListState
import com.example.psychoremstered.R
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ClientUI(
    clientState: ClientState,
    onClientEvent: (ClientEvent) -> Unit,
    listState: TherapistListState,
    onListEvent: (TherapistListEvent) -> Unit,
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
    val windowClass = calculateWindowSizeClass(activity = LocalContext.current as Activity)
    val showNavigationRail =
        windowClass.widthSizeClass != WindowWidthSizeClass.Compact

    Scaffold(
        topBar = {
            if (!showNavigationRail) {
                TopAppBar(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .offset(y = (-1).dp),
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
                        IconToggleButton(
                            checked = clientState.showFavouriteTherapists,
                            onCheckedChange = {
                                onClientEvent(ClientEvent.ShowFavouriteTherapists(!clientState.showFavouriteTherapists))
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                tint = Color.Black,
                                imageVector = if (clientState.showFavouriteTherapists) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Default.FavoriteBorder
                                },
                                contentDescription = "Favourite"
                            )
                        }
                        IconButton(onClick = {
                            onClientEvent(
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
            }
        },
        bottomBar = {
            if (!showNavigationRail) {
                NavigationBar(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .offset(y = (1).dp),
                    containerColor = colorResource(id = R.color.white)
                ) {
                    navItems.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                NavigationIcon(
                                    item = navigationItem,
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
    ) { padding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = if (showNavigationRail) 80.dp else padding.calculateStartPadding(
                        LayoutDirection.Ltr
                    ),
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            navController = clientNavController,
            startDestination = ClientScreenRoutes.TherapistListScreen
        ) {
            composable<ClientScreenRoutes.TherapistListScreen> {
                navigationSelectedItem = 0
                title = navItems[navigationSelectedItem].label
                TherapistListScreen(
                    state = listState,
                    onEvent = onListEvent,
                    navController = clientNavController,
                    showFavouriteTherapists = clientState.showFavouriteTherapists
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
        if (showNavigationRail) {
            NavigationRail(
                header = {
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
                    IconToggleButton(
                        checked = clientState.showFavouriteTherapists,
                        onCheckedChange = {
                            onClientEvent(ClientEvent.ShowFavouriteTherapists(!clientState.showFavouriteTherapists))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            tint = Color.Black,
                            imageVector = if (clientState.showFavouriteTherapists) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = "Favourite"
                        )
                    }
                    IconButton(onClick = {
                        onClientEvent(
                            ClientEvent.SignOut(navController)
                        )
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Sing out"
                        )
                    }
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .offset(x = (-1).dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom)
                ) {
                    navItems.forEachIndexed { index, navigationItem ->
                        NavigationRailItem(
                            selected = index == navigationSelectedItem,
                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                NavigationIcon(
                                    item = navigationItem,
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
    }
}

@Composable
fun NavigationIcon(
    item: BottomNavigationItem
) {
    BadgedBox(
        badge = {
            if (item.badgeCount != null) {
                Badge {
                    Text(text = item.badgeCount.toString())
                }
            }
            if (item.hasNews) {
                Badge()
            }
        }
    ) {
        Icon(
            item.icon,
            contentDescription = item.label
        )
    }
}