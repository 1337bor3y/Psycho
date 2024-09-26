package com.example.psychoremastered.presentation.choose

data class ChooseState(
    val isLoading: Boolean = false,
    val authError: String? = null,
    val isClient: Boolean = true,
    val isCurrentUserSignedIn: Boolean = false,
    val isChooseDialogOpened: Boolean = false
)