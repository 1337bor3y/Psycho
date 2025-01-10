package com.example.psychoremastered.presentation.therapist_list

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.psychoremastered.core.screen_route.ClientScreenRoutes
import com.example.psychoremastered.presentation.therapist_list.component.TherapistListItem

@Composable
fun TherapistListScreen(
    state: TherapistListState,
    onEvent: (TherapistListEvent) -> Unit,
    navController: NavController,
    showFavouriteTherapists: Boolean
) {
    val windowPadding = WindowInsets.navigationBars.asPaddingValues()
    val therapists = state.therapists.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = windowPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = windowPadding.calculateEndPadding(LayoutDirection.Ltr)
            )
    ) {
        if (!showFavouriteTherapists) {
            when (therapists.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

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
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(therapists.itemCount) { therapistIndex ->
                            if (therapists[therapistIndex] != null) {
                                therapists[therapistIndex]?.let { therapist ->
                                    TherapistListItem(
                                        therapist = therapist,
                                        onItemClick = {
                                            navController.navigate(
                                                ClientScreenRoutes.PreviewTherapistScreen(therapist)
                                            )
                                        },
                                        isFavoriteTherapist = state.favouriteTherapist.contains(
                                            therapist
                                        ),
                                        onEvent = onEvent
                                    )
                                }
                            }
                        }
                        item {
                            if (therapists.loadState.append is LoadState.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.favouriteTherapist) { therapist ->
                    TherapistListItem(
                        therapist = therapist,
                        onItemClick = {
                            navController.navigate(
                                ClientScreenRoutes.PreviewTherapistScreen(therapist)
                            )
                        },
                        isFavoriteTherapist = true,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}