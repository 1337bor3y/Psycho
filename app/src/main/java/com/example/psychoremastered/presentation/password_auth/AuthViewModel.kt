package com.example.psychoremastered.presentation.password_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.psychoremastered.core.ScreenRoutes
import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import com.example.psychoremastered.domain.use_case.GetClientUseCase
import com.example.psychoremastered.domain.use_case.GetIsClientPreferenceUseCase
import com.example.psychoremastered.domain.use_case.GetTherapistUseCase
import com.example.psychoremastered.domain.use_case.SaveClientUseCase
import com.example.psychoremastered.domain.use_case.SignInWithEmailAndPasswordUseCase
import com.example.psychoremastered.domain.use_case.ValidateConfirmPasswordUseCase
import com.example.psychoremastered.domain.use_case.ValidateEmailUseCase
import com.example.psychoremastered.domain.use_case.ValidateFirstNameUseCase
import com.example.psychoremastered.domain.use_case.ValidatePasswordUseCase
import com.example.psychoremastered.domain.use_case.ValidateProfileImageUseCase
import com.example.psychoremastered.domain.use_case.ValidateSurnameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase,
    private val validateProfileImageUseCase: ValidateProfileImageUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateSurnameUseCase: ValidateSurnameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val saveClientUseCase: SaveClientUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val getTherapistUseCase: GetTherapistUseCase,
    private val getIsClientPreferenceUseCase: GetIsClientPreferenceUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isClient = getIsClientPreferenceUseCase() ?: true
                )
            }
        }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.CreateUserWithEmailAndPassword -> createUserWithEmailAndPassword(
                event.navController
            )

            is AuthEvent.SignInWithEmailAndPassword -> signInWithEmailAndPassword(
                event.navController
            )

            is AuthEvent.SetConfirmPassword -> _state.update {
                it.copy(
                    confirmPassword = event.confirmPassword,
                    confirmPasswordError = "",
                    isConfirmPasswordValid = true
                )
            }

            is AuthEvent.SetEmail -> _state.update {
                it.copy(
                    email = event.email,
                    emailError = "",
                    isEmailValid = true
                )
            }

            is AuthEvent.SetFirstName -> _state.update {
                it.copy(
                    firstName = event.firstName,
                    firstNameError = "",
                    isFirstNameValid = true
                )
            }

            is AuthEvent.SetPassword -> _state.update {
                it.copy(
                    password = event.password,
                    passwordError = "",
                    isPasswordValid = true
                )
            }

            is AuthEvent.SetProfileImage -> _state.update {
                it.copy(
                    profileImage = event.profileImage,
                    profileImageError = "",
                    isProfileImageValid = true
                )
            }

            is AuthEvent.SetSurname -> _state.update {
                it.copy(
                    surname = event.surname,
                    surnameError = "",
                    isSurnameValid = true
                )
            }
        }
    }

    private fun createUserWithEmailAndPassword(navController: NavController) {
        viewModelScope.launch {
            validateSignUpData()
            if (state.value.isProfileImageValid && state.value.isFirstNameValid &&
                state.value.isSurnameValid && state.value.isEmailValid &&
                state.value.isPasswordValid && state.value.isConfirmPasswordValid
            )
                createUserWithEmailAndPasswordUseCase(
                    authEmail = state.value.email,
                    authPassword = state.value.password
                ).onEach { result ->
                    when (result) {
                        is Resource.Error -> _state.update {
                            it.copy(
                                authError = result.errorMessage,
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> _state.update {
                            it.copy(
                                authError = null,
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false
                                )
                            }
                            result.data?.run {
                                if (state.value.isClient) {
                                    saveClientUseCase(
                                        Client(
                                            id = userId,
                                            displayName = _state.value.firstName
                                                    + " " + _state.value.surname,
                                            email = email ?: "",
                                            avatarUri = _state.value.profileImage
                                        )
                                    )
                                    // Navigate to client ui
                                } else {
                                    navController.navigate(
                                        ScreenRoutes.TherapistRegistrationScreen(
                                            userId = userId,
                                            email = email ?: "",
                                            displayName = _state.value.firstName
                                                    + " " + _state.value.surname,
                                            profilePictureUri = _state.value.profileImage
                                        )
                                    )
                                }
                            }
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun signInWithEmailAndPassword(navController: NavController) {
        viewModelScope.launch {
            validateSignInData()
            if (state.value.isEmailValid && state.value.isPasswordValid) {
                signInWithEmailAndPasswordUseCase(
                    authEmail = state.value.email,
                    authPassword = state.value.password
                ).onEach { result ->
                    when (result) {
                        is Resource.Error -> _state.update {
                            it.copy(
                                authError = result.errorMessage,
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> _state.update {
                            it.copy(
                                authError = null,
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false
                                )
                            }
                            result.data?.run {
                                if (state.value.isClient) {
                                    getClientUseCase(userId).firstOrNull()?.run {
                                        // Navigate to client ui
                                    } ?: run {
                                        getTherapistUseCase(userId).firstOrNull()?.let {
                                            saveClientUseCase(
                                                Client(
                                                    id = it.id,
                                                    displayName = it.displayName,
                                                    email = it.email,
                                                    avatarUri = it.avatarUri
                                                )
                                            )
                                            // Navigate to client ui
                                        }
                                    }
                                } else {
                                    getTherapistUseCase(userId).firstOrNull()?.run {
                                        // Navigate to therapist ui
                                    } ?: run {
                                        getClientUseCase(userId).firstOrNull()?.let {
                                            navController.navigate(
                                                    ScreenRoutes.TherapistRegistrationScreen(
                                                        userId = it.id,
                                                        email = it.email,
                                                        displayName = it.displayName,
                                                        profilePictureUri = it.avatarUri
                                                    )
                                                )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private suspend fun validateSignUpData() {
        _state.update {
            it.copy(
                isProfileImageValid = validateProfileImageUseCase(state.value.profileImage)
                    .first().data ?: true,
                profileImageError = validateProfileImageUseCase(state.value.profileImage)
                    .last().errorMessage ?: "",
                isFirstNameValid = validateFirstNameUseCase(state.value.firstName)
                    .first().data ?: true,
                firstNameError = validateFirstNameUseCase(state.value.firstName)
                    .last().errorMessage ?: "",
                isSurnameValid = validateSurnameUseCase(state.value.surname)
                    .first().data ?: true,
                surnameError = validateSurnameUseCase(state.value.surname)
                    .last().errorMessage ?: "",
                isEmailValid = validateEmailUseCase(state.value.email)
                    .first().data ?: true,
                emailError = validateEmailUseCase(state.value.email)
                    .last().errorMessage ?: "",
                isPasswordValid = validatePasswordUseCase(state.value.password)
                    .first().data ?: true,
                passwordError = validatePasswordUseCase(state.value.password)
                    .last().errorMessage ?: "",
                isConfirmPasswordValid = validateConfirmPasswordUseCase(
                    state.value.password,
                    state.value.confirmPassword
                ).first().data ?: true,
                confirmPasswordError = validateConfirmPasswordUseCase(
                    state.value.password,
                    state.value.confirmPassword
                ).last().errorMessage ?: ""
            )
        }

    }

    private suspend fun validateSignInData() {
        _state.update {
            it.copy(
                isEmailValid = validateEmailUseCase(state.value.email)
                    .first().data ?: true,
                emailError = validateEmailUseCase(state.value.email)
                    .last().errorMessage ?: "",
                isPasswordValid = validatePasswordUseCase(state.value.password)
                    .first().data ?: true,
                passwordError = validatePasswordUseCase(state.value.password)
                    .last().errorMessage ?: ""
            )
        }
    }
}