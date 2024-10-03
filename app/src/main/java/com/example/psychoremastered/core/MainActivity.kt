package com.example.psychoremastered.core

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.psychoremastered.core.screen_route.ClientScreenRoutes
import com.example.psychoremastered.core.screen_route.MainScreenRoutes
import com.example.psychoremastered.core.ui.theme.PsychoRemsteredTheme
import com.example.psychoremastered.domain.model.User
import com.example.psychoremastered.presentation.choose.ChooseScreen
import com.example.psychoremastered.presentation.choose.ChooseViewModel
import com.example.psychoremastered.presentation.client.ClientUI
import com.example.psychoremastered.presentation.client.ClientViewModel
import com.example.psychoremastered.presentation.client.chat.ClientChatsScreen
import com.example.psychoremastered.presentation.client.proflie.ClientProfileScreen
import com.example.psychoremastered.presentation.client.therapist_list.TherapistListScreen
import com.example.psychoremastered.presentation.password_auth.AuthViewModel
import com.example.psychoremastered.presentation.password_auth.PasswordAuthUI
import com.example.psychoremastered.presentation.therapist_registration.RegistrationEvent
import com.example.psychoremastered.presentation.therapist_registration.RegistrationUI
import com.example.psychoremastered.presentation.therapist_registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        enableEdgeToEdge()
        setContent {
            PsychoRemsteredTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = MainScreenRoutes.ChooseScreen
                ) {
                    composable<MainScreenRoutes.ChooseScreen> {
                        val chooseViewModel = hiltViewModel<ChooseViewModel>()
                        val chooseState by chooseViewModel.state.collectAsStateWithLifecycle()
                        ChooseScreen(
                            state = chooseState,
                            onEvent = chooseViewModel::onEvent,
                            navController = navController
                        )
                    }
                    composable<MainScreenRoutes.PasswordAuthScreen> {
                        val authViewModel = hiltViewModel<AuthViewModel>()
                        val authState by authViewModel.state.collectAsStateWithLifecycle()
                        PasswordAuthUI(
                            state = authState,
                            onEvent = authViewModel::onEvent,
                            navController = navController
                        )
                    }
                    composable<MainScreenRoutes.TherapistRegistrationScreen> {
                        val user = it.toRoute<MainScreenRoutes.TherapistRegistrationScreen>()
                        val regViewModel = hiltViewModel<RegistrationViewModel>()
                        val regState by regViewModel.state.collectAsStateWithLifecycle()
                        regViewModel.onEvent(
                            RegistrationEvent.SetUser(
                                user = User(
                                    userId = user.userId,
                                    email = user.email,
                                    displayName = user.displayName,
                                    profilePictureUri = user.profilePictureUri
                                )
                            )
                        )
                        RegistrationUI(
                            state = regState,
                            onEvent = regViewModel::onEvent,
                            navController = navController,
                        )
                    }
                    composable<MainScreenRoutes.ClientScreen> {
                        val clientViewModel = hiltViewModel<ClientViewModel>()
                        ClientUI(
                            onEvent = clientViewModel::onEvent,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}