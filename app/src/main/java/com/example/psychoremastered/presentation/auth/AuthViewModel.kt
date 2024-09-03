package com.example.psychoremastered.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.domain.use_case.SignInWithCredential
import com.example.psychoremastered.domain.model.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithCredential: SignInWithCredential
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInWithGoogle -> signInWithGoogle(event.result)
        }
    }

    private fun signInWithGoogle(result: SignInResult) {
        viewModelScope.launch {
            var user: User? = null
            if (result.idToken != null) {
                user = signInWithCredential(result.idToken)
            }
            _state.update {
                it.copy(
                    user = user,
                    signInError = result.errorMessage
                )
            }
        }
    }
}