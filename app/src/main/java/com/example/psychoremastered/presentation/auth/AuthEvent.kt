package com.example.psychoremastered.presentation.auth

import com.example.psychoremastered.domain.model.SignInResult

sealed interface AuthEvent {
    data class SignInWithGoogle(val result: SignInResult): AuthEvent
}