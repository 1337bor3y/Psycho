package com.example.psychoremastered.presentation.choose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.psychoremastered.core.screen_route.MainScreenRoutes
import com.example.psychoremastered.domain.model.Client
import com.example.psychoremastered.domain.model.GoogleSignInResult
import com.example.psychoremastered.domain.model.Resource
import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.domain.use_case.GetClientUseCase
import com.example.psychoremastered.domain.use_case.GetCurrentUserUseCase
import com.example.psychoremastered.domain.use_case.GetTherapistUseCase
import com.example.psychoremastered.domain.use_case.PutIsClientPreferenceUseCase
import com.example.psychoremastered.domain.use_case.SaveClientUseCase
import com.example.psychoremastered.domain.use_case.SignInWithCredentialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseViewModel @Inject constructor(
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val saveClientUseCase: SaveClientUseCase,
    private val putIsClientPreferenceUseCase: PutIsClientPreferenceUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val getTherapistUseCase: GetTherapistUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChooseState())
    val state = _state.asStateFlow()

    fun onEvent(event: ChooseEvent) {
        when (event) {
            is ChooseEvent.SignInWithGoogle -> signInWithGoogle(
                event.result,
                event.navController
            )

            ChooseEvent.ChooseClient -> putIsClientPreference(isClient = true)

            ChooseEvent.ChooseTherapist -> putIsClientPreference(isClient = false)

            is ChooseEvent.IsCurrentUserSignedIn -> isCurrentUserSignedIn(event.navController)

            is ChooseEvent.OpenChooseDialog -> openChooseDialog(event.open)
        }
    }

    private fun openChooseDialog(open: Boolean) {
        _state.update {
            it.copy(
                isChooseDialogOpened = open
            )
        }
    }

    private fun isCurrentUserSignedIn(navController: NavController) {
        viewModelScope.launch {
            getCurrentUserUseCase()?.let {
                signInExistingUser(it, navController)
            } ?: openChooseDialog(true)
        }
    }

    private suspend fun signInExistingUser(user: User, navController: NavController) {
        if (state.value.isClient) {
            getClientUseCase(user.userId).firstOrNull()?.run {
                navController.navigate(MainScreenRoutes.ClientScreen)
            } ?: run {
                getTherapistUseCase(user.userId).firstOrNull()?.let {
                    saveClientUseCase(
                        Client(
                            id = it.id,
                            displayName = it.displayName,
                            email = it.email,
                            avatarUri = it.avatarUri
                        )
                    )
                    navController.navigate(MainScreenRoutes.ClientScreen)
                } ?: run {
                    saveClientUseCase(
                        Client(
                            id = user.userId,
                            email = user.email ?: "",
                            displayName = user.email ?: "",
                            avatarUri = user.profilePictureUri ?: ""
                        )
                    )
                    navController.navigate(MainScreenRoutes.ClientScreen)
                }
            }
        } else {
            getTherapistUseCase(user.userId).firstOrNull()?.run {
                // Navigate to therapist ui
            } ?: run {
                getClientUseCase(user.userId).firstOrNull()?.let {
                    navController.navigate(
                        MainScreenRoutes.TherapistRegistrationScreen(
                            User(
                                userId = it.id,
                                email = it.email,
                                displayName = it.displayName,
                                profilePictureUri = it.avatarUri
                            )
                        )
                    )
                } ?: run {
                    navController.navigate(
                        MainScreenRoutes.TherapistRegistrationScreen(user)
                    )
                }
            }
        }
    }

    private fun signInWithGoogle(signInResult: GoogleSignInResult, navController: NavController) {
        signInResult.idToken?.let { token ->
            signInWithCredentialUseCase(token).onEach { result ->
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
                                getClientUseCase(userId).firstOrNull()?.let {
                                    navController.navigate(MainScreenRoutes.ClientScreen)
                                } ?: run {
                                    saveClientUseCase(
                                        Client(
                                            id = userId,
                                            displayName = displayName ?: "",
                                            email = email ?: "",
                                            avatarUri = profilePictureUri ?: ""
                                        )
                                    )
                                    navController.navigate(MainScreenRoutes.ClientScreen)
                                }
                            } else {
                                getTherapistUseCase(userId).firstOrNull()?.let {
                                    // Navigate to therapist ui
                                } ?: run {
                                    navController.navigate(
                                        MainScreenRoutes.TherapistRegistrationScreen(
                                            User(
                                                userId = userId,
                                                email = email,
                                                displayName = displayName,
                                                profilePictureUri = profilePictureUri
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
        } ?: _state.update {
            it.copy(
                authError = signInResult.errorMessage
            )
        }
    }

    private fun putIsClientPreference(isClient: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isClient = isClient
                )
            }
            putIsClientPreferenceUseCase(value = isClient)
        }
    }
}