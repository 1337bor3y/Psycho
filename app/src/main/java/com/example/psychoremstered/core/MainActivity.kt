package com.example.psychoremstered.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.psychoremstered.core.ui.theme.PsychoRemsteredTheme
import com.example.psychoremstered.therapist_registration.RegistrationUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsychoRemsteredTheme {
                RegistrationUI()
            }
        }
    }
}