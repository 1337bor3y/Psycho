package com.example.psychoremastered.presentation.auth

data class AuthState(
    val isLoading: Boolean = false,
    val authError: String? = null,
    val email: String = "",
    val firstName: String = "",
    val surname: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val profileImage: String = "",
    val isProfileImageValid: Boolean = true,
    val profileImageError: String = "",
    val isFirstNameValid: Boolean = true,
    val firstNameError: String = "",
    val isSurnameValid: Boolean = true,
    val surnameError: String = "",
    val isEmailValid: Boolean = true,
    val emailError: String = "",
    val isPasswordValid: Boolean = true,
    val passwordError: String = "",
    val isConfirmPasswordValid: Boolean = true,
    val confirmPasswordError: String = "",
    val isClient: Boolean = true,
    val isCurrentUserSignedIn: Boolean = false,
    val isChooseDialogOpened: Boolean = false
)