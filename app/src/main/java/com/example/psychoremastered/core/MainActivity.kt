package com.example.psychoremastered.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.psychoremastered.core.ui.theme.PsychoRemsteredTheme
import com.example.psychoremastered.presentation.therapist_registration.RegistrationUI
import com.example.psychoremastered.presentation.therapist_registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsychoRemsteredTheme {
                val viewModel by viewModels<RegistrationViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                RegistrationUI(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}