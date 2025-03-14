package com.example.psychoremastered.presentation.therapist_list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.psychoremastered.core.screen_route.ClientScreenRoutes
import com.example.psychoremastered.presentation.therapist_list.component.TherapistListItem

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TherapistListScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    state: TherapistListState,
    onEvent: (TherapistListEvent) -> Unit,
    navController: NavController,
    showFavouriteTherapists: Boolean
) {
    val windowPadding = WindowInsets.navigationBars.asPaddingValues()
    val therapists = state.therapists.collectAsLazyPagingItems()
    var isFavTherapistsEmpty by rememberSaveable {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = windowPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = windowPadding.calculateEndPadding(LayoutDirection.Ltr)
            )
    ) {
        when (therapists.loadState.refresh) {
            is LoadState.Loading -> {}

            is LoadState.Error -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "${(therapists.loadState.refresh as LoadState.Error).error.message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!showFavouriteTherapists) {
                        items(therapists.itemCount, key = { therapists[it]?.id ?: it }) { therapistIndex ->
                            if (therapists[therapistIndex] != null) {
                                therapists[therapistIndex]?.let { therapist ->
                                    TherapistListItem(
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        therapist = therapist,
                                        onItemClick = {
                                            navController.navigate(
                                                ClientScreenRoutes.PreviewTherapistScreen(therapist)
                                            )
                                        },
                                        isFavoriteTherapist = state.favouriteTherapists.contains(
                                            therapist
                                        ),
                                        onEvent = onEvent,
                                        isFavoriteTherapistScreen = false
                                    )
                                }
                            }
                        }
                    } else {
                        onEvent(
                            TherapistListEvent.ShowFavouriteTherapists
                        )
                        isFavTherapistsEmpty = state.favouriteTherapists.isEmpty()
                        items(state.favouriteTherapists, key = { it.id }) { therapist ->
                            TherapistListItem(
                                sharedTransitionScope = sharedTransitionScope,
                                animatedVisibilityScope = animatedVisibilityScope,
                                therapist = therapist,
                                onItemClick = {
                                    navController.navigate(
                                        ClientScreenRoutes.PreviewTherapistScreen(therapist)
                                    )
                                },
                                isFavoriteTherapist = true,
                                onEvent = onEvent,
                                isFavoriteTherapistScreen = true
                            )
                        }
                    }
                }
            }
        }
        if (showFavouriteTherapists && isFavTherapistsEmpty) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "There are no favourite therapists",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}