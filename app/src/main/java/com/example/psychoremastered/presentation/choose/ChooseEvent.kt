package com.example.psychoremastered.presentation.choose

import androidx.navigation.NavController
import com.example.psychoremastered.domain.model.GoogleSignInResult

sealed interface ChooseEvent {
    data class SignInWithGoogle(
        val result: GoogleSignInResult,
        val navController: NavController
    ) : ChooseEvent
    data object ChooseClient: ChooseEvent
    data object ChooseTherapist: ChooseEvent
    data class IsCurrentUserSignedIn(val navController: NavController) : ChooseEvent
    data class OpenChooseDialog(val open: Boolean): ChooseEvent
}