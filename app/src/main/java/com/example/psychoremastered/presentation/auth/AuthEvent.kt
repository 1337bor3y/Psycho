package com.example.psychoremastered.presentation.auth

import com.example.psychoremastered.domain.model.GoogleSignInResult

sealed interface AuthEvent {
    data class SignInWithGoogle(val result: GoogleSignInResult) : AuthEvent
    data object SignInWithEmailAndPassword : AuthEvent
    data object CreateUserWithEmailAndPassword : AuthEvent
    data class SetEmail(val email: String) : AuthEvent
    data class SetFirstName(val firstName: String) : AuthEvent
    data class SetSurname(val surname: String) : AuthEvent
    data class SetPassword(val password: String) : AuthEvent
    data class SetConfirmPassword(val confirmPassword: String) : AuthEvent
    data class SetProfileImage(val profileImage: String) : AuthEvent
}