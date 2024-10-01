package com.example.psychoremastered.presentation.choose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.psychoremstered.R

@Composable
fun ChooseScreen(
    state: ChooseState,
    onEvent: (ChooseEvent) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    if (state.isChooseDialogOpened) {
        ChooseDialog(
            onEvent = onEvent,
            navController = navController,
            onDismissRequest = {
                onEvent(
                    ChooseEvent.OpenChooseDialog(false)
                )
            }
        )
    }

    LaunchedEffect(key1 = state.authError) {
        state.authError?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            Text(
                text = stringResource(R.string.choose_ask_text),
                style = MaterialTheme
                    .typography.headlineMedium
                    .copy(
                        color = Color.White,
                        fontFamily = FontFamily.Serif
                    )
            )
            Spacer(modifier = Modifier.height(150.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 54.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Blue,
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    onEvent(
                        ChooseEvent.ChooseClient
                    )
                    onEvent(
                        ChooseEvent.IsCurrentUserSignedIn(navController)
                    )
                }) {
                Text(
                    text = stringResource(R.string.client),
                    style = MaterialTheme
                        .typography.titleLarge
                        .copy(
                            fontFamily = FontFamily.Serif
                        )
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 54.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Blue,
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    onEvent(
                        ChooseEvent.ChooseTherapist
                    )
                    onEvent(
                        ChooseEvent.IsCurrentUserSignedIn(navController)
                    )
                }) {
                Text(
                    text = stringResource(R.string.therapist),
                    style = MaterialTheme
                        .typography.titleLarge
                        .copy(
                            fontFamily = FontFamily.Serif
                        )
                )
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(70.dp)
            )
        }
    }
}