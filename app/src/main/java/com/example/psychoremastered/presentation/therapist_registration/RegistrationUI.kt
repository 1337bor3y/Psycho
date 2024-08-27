package com.example.psychoremastered.presentation.therapist_registration

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psychoremastered.domain.model.Degree
import com.example.psychoremastered.presentation.therapist_registration.model.registrationPages
import com.example.psychoremstered.R
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationUI(
    state: RegistrationState,
    onEvent: (RegistrationEvent) -> Unit
) {
    var pageCount by rememberSaveable {
        mutableIntStateOf(registrationPages.size)
    }
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 44.dp),
    ) {
        TopAppBar(
            title = { Text(text = "") },
            navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage != 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        } else {
                            Toast.makeText(
                                context, "Navigate to welcome screen",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigation
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Get back"
                    )
                }
            }
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f, false),
            verticalAlignment = Alignment.Top
        ) { page ->
            if (page <= 2) {
                CheckBoxesRegistrationScreen(
                    page = registrationPages[page],
                    onEvent = onEvent,
                    pageOffset = (
                            (pagerState.currentPage - page)
                                    + pagerState.currentPageOffsetFraction
                            ).absoluteValue
                )
            } else if (page in 3..4) {
                TextFieldRegistrationScreen(
                    page = registrationPages[page],
                    state = state,
                    onEvent = onEvent,
                    pageOffset = (
                            (pagerState.currentPage - page)
                                    + pagerState.currentPageOffsetFraction
                            ).absoluteValue
                )
            } else {
                DegreeRegistrationScreen(
                    page = registrationPages[5],
                    pageOffset = (
                            (pagerState.currentPage - page)
                                    + pagerState.currentPageOffsetFraction
                            ).absoluteValue,
                    degree = try {
                        state.degrees[page - 5]
                    } catch (e: IndexOutOfBoundsException) {
                        Degree(
                            id = page - 5,
                            university = "",
                            speciality = "",
                            admissionYear = "",
                            graduationYear = "",
                            documentImage = ""
                        )
                    },
                    onEvent = onEvent,
                    addOnClick = {
                        coroutineScope.launch {
                            onEvent(
                                RegistrationEvent.AddDegree(page - 5)
                            )
                            pageCount++
                            pagerState.animateScrollToPage(page + 1)
                        }
                    },
                    removeOnClick = {
                        coroutineScope.launch {
                            onEvent(
                                RegistrationEvent.RemoveDegree(page - 5)
                            )
                            pagerState.animateScrollToPage(page - 1)
                            --pageCount
                        }
                    }
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 10.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage != pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        Toast.makeText(
                            context, "Navigate to therapist screen",
                            Toast.LENGTH_SHORT
                        ).show()
                        onEvent(
                            RegistrationEvent.AddDegree(pagerState.currentPage - 5)
                        )
                        // Navigation
                    }
                }
            }
        ) {
            Text(text = context.getString(R.string.submit), fontSize = 14.sp)
        }
    }
}