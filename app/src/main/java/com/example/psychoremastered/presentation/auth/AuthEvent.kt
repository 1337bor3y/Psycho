package com.example.psychoremastered.presentation.auth

import androidx.navigation.NavController
import com.example.psychoremastered.domain.model.GoogleSignInResult

sealed interface AuthEvent {
    data class SignInWithGoogle(
        val result: GoogleSignInResult,
        val navController: NavController
    ) : AuthEvent
    data class SignInWithEmailAndPassword(val navController: NavController) : AuthEvent
    data class CreateUserWithEmailAndPassword(val navController: NavController) : AuthEvent
    data class SetEmail(val email: String) : AuthEvent
    data class SetFirstName(val firstName: String) : AuthEvent
    data class SetSurname(val surname: String) : AuthEvent
    data class SetPassword(val password: String) : AuthEvent
    data class SetConfirmPassword(val confirmPassword: String) : AuthEvent
    data class SetProfileImage(val profileImage: String) : AuthEvent
    data object ChooseClient: AuthEvent
    data object ChooseTherapist: AuthEvent
}