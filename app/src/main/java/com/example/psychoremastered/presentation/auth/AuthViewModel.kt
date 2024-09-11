package com.example.psychoremastered.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psychoremastered.domain.model.GoogleSignInResult
import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import com.example.psychoremastered.domain.use_case.SignInWithCredentialUseCase
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
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase,
    private val validateProfileImageUseCase: ValidateProfileImageUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateSurnameUseCase: ValidateSurnameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInWithGoogle -> signInWithGoogle(event.result)

            AuthEvent.CreateUserWithEmailAndPassword -> createUserWithEmailAndPassword()

            AuthEvent.SignInWithEmailAndPassword -> signInWithEmailAndPassword()

            is AuthEvent.SetConfirmPassword -> _state.update {
                it.copy(
                    confirmPassword = event.confirmPassword
                )
            }

            is AuthEvent.SetEmail -> _state.update {
                it.copy(
                    email = event.email
                )
            }

            is AuthEvent.SetFirstName -> _state.update {
                it.copy(
                    firstName = event.firstName
                )
            }

            is AuthEvent.SetPassword -> _state.update {
                it.copy(
                    password = event.password
                )
            }

            is AuthEvent.SetProfileImage -> _state.update {
                it.copy(
                    profileImage = event.profileImage
                )
            }

            is AuthEvent.SetSurname -> _state.update {
                it.copy(
                    surname = event.surname
                )
            }
        }
    }

    private fun signInWithGoogle(signInResult: GoogleSignInResult) {
        signInResult.idToken?.let { token ->
            signInWithCredentialUseCase(token).onEach { result ->
                when (result) {
                    is Resource.Error -> _state.update {
                        it.copy(
                            signInError = result.errorMessage,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> _state.update {
                        it.copy(
                            signInError = null,
                            isLoading = true
                        )
                    }

                    is Resource.Success -> _state.update {
                        it.copy(
                            user = result.data,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        } ?: _state.update {
            it.copy(
                user = null,
                signInError = signInResult.errorMessage
            )
        }
    }

    private fun createUserWithEmailAndPassword() {
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
                                signInError = result.errorMessage,
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> _state.update {
                            it.copy(
                                signInError = null,
                                isLoading = true
                            )
                        }

                        is Resource.Success -> _state.update {
                            it.copy(
                                user = result.data,
                                isLoading = false
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    private fun signInWithEmailAndPassword() {
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
                                signInError = result.errorMessage,
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> _state.update {
                            it.copy(
                                signInError = null,
                                isLoading = true
                            )
                        }

                        is Resource.Success -> _state.update {
                            it.copy(
                                user = result.data,
                                isLoading = false
                            )
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