package com.example.psychoremastered.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psychoremastered.domain.use_case.SignInWithCredentialUseCase
import com.example.psychoremastered.domain.model.GoogleSignInResult
import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import com.example.psychoremastered.domain.use_case.SignInWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPasswordUseCase
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

    private fun signInWithEmailAndPassword() {
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