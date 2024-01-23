package com.bitvolper.yogazzz.presentation.onboarding

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bitvolper.yogazzz.presentation.onboarding.login.LoginScreen
import com.bitvolper.yogazzz.presentation.onboarding.login.LoginWithPasswordScreen
import com.bitvolper.yogazzz.presentation.onboarding.signup.SignupWithPasswordScreen
import com.bitvolper.yogazzz.presentation.viewmodel.OnboardingViewModel

@Composable
fun OnboardingApp(modifier: Modifier = Modifier,
                  navController: NavHostController = rememberNavController(),
                  onboardingViewModel: OnboardingViewModel = hiltViewModel()) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            OnboardingTopAppbar()
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        NavHost(modifier = modifier.padding(paddingValues), navController = navController, startDestination = OnboardingScreen.Start.name) {

            composable(route = OnboardingScreen.Start.name) {
                OnboardingScreen(modifier = modifier) {
                    navController.navigate(OnboardingScreen.Login.name)
                }
            }

            composable(route = OnboardingScreen.Login.name) {
                LoginScreen(
                    onSignInClick = {
                        navController.navigate(OnboardingScreen.LoginWithPassword.name)
                    },
                    onSignUpClick = {
                        navController.navigate(OnboardingScreen.SignupWithPassword.name)
                    }
                )
            }

            composable(route = OnboardingScreen.LoginWithPassword.name) {
                LoginWithPasswordScreen(
                    onResetPasswordClick = {
//                        navController.navigate(OnboardingScreen.ResetPassword.name)
                    },
                    onLoginClick = { email, password ->
//                        onboardingViewModel.login(email, password)
                    },
//                    loginUIState = onboardingViewModel.loginUIState,
                    snackbarHostState = snackbarHostState
                )
            }

            composable(route = OnboardingScreen.SignupWithPassword.name) {
                SignupWithPasswordScreen(
                    onSignupClick = { email, password ->
//                        onboardingViewModel.register(email, password)
                    },
//                    registerUIState = onboardingViewModel.registerUIState,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OnboardingTopAppbar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {  },
        navigationIcon = { IconButton(onClick = { }) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
        } },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

enum class OnboardingScreen {
    Start, Login, LoginWithPassword, SignupWithPassword,
//    ResetPassword, CodeVerification, CreateNewPassword,

}