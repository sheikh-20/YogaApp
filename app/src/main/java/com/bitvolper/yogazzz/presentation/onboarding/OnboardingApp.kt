package com.bitvolper.yogazzz.presentation.onboarding

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bitvolper.yogazzz.presentation.dialog.LoginDialog
import com.bitvolper.yogazzz.presentation.dialog.SignupDialog
import com.bitvolper.yogazzz.presentation.onboarding.login.LoginScreen
import com.bitvolper.yogazzz.presentation.onboarding.login.LoginWithPasswordScreen
import com.bitvolper.yogazzz.presentation.onboarding.signup.SignupWithPasswordScreen
import com.bitvolper.yogazzz.presentation.viewmodel.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingApp(modifier: Modifier = Modifier,
                  navController: NavHostController = rememberNavController(),
                  onboardingViewModel: OnboardingViewModel = hiltViewModel()) {

    val snackbarHostState = remember { SnackbarHostState() }
    val onSocialSignIn = onboardingViewModel.socialSignIn

    val loginUIState by onboardingViewModel.loginUIState.collectAsState()
    val signupUIState by onboardingViewModel.signupUIState.collectAsState()

    var showOnboardDialog by remember { mutableStateOf(ShowOnboardDialog.Default) }

    when (showOnboardDialog) {
        ShowOnboardDialog.Default -> {  }
        ShowOnboardDialog.Login -> {
            AlertDialog(onDismissRequest = { /*TODO*/ }) {
                LoginDialog()
            }
        }
        ShowOnboardDialog.Signup -> {
            AlertDialog(onDismissRequest = { /*TODO*/ }) {
                SignupDialog()
            }
        }
    }

    Scaffold(
        topBar = {
            OnboardingTopAppbar(navController = navController)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = OnboardingScreen.Start.name) {

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
                    },
                    onGoogleSignInClick = { activity, intent ->  onboardingViewModel.signInGoogle(activity, intent) },
                    onSocialSignIn = onSocialSignIn,
                    snackbarHostState = snackbarHostState,
                    showDialog = { showOnboardDialog = it },
                    onUpdateUserProfile = onboardingViewModel::updateUserProfile
                )
            }

            composable(route = OnboardingScreen.LoginWithPassword.name) {
                LoginWithPasswordScreen(
                    paddingValues = paddingValues,
                    onResetPasswordClick = {
//                        navController.navigate(OnboardingScreen.ResetPassword.name)
                    },
                    onSignInClick = { email: String?, password: String? -> onboardingViewModel.signInEmail(email, password) },
                    onGoogleSignInClick = { activity, intent ->  onboardingViewModel.signInGoogle(activity, intent) },
                    onSocialSignIn = onSocialSignIn,
                    email = onboardingViewModel.email,
                    onEmailChange = onboardingViewModel::onEmailChange,
                    snackbarHostState = snackbarHostState,
                    loginUIState = loginUIState,
                    showDialog = { showOnboardDialog = it },
                    onUpdateUserProfile = onboardingViewModel::updateUserProfile
                )
            }

            composable(route = OnboardingScreen.SignupWithPassword.name) {
                SignupWithPasswordScreen(
                    paddingValues = paddingValues,
                    onSignupClick = { email: String?, password: String? -> onboardingViewModel.signUpEmail(email, password) },
                    snackbarHostState = snackbarHostState,
                    onGoogleSignInClick = { activity, intent ->  onboardingViewModel.signInGoogle(activity, intent) },
                    onSocialSignIn = onSocialSignIn,
                    signupUIState = signupUIState,
                    showDialog = { showOnboardDialog = it },
                    onUpdateUserProfile = onboardingViewModel::updateUserProfile
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OnboardingTopAppbar(navController: NavHostController) {

    when (navController.currentBackStackEntryAsState().value?.destination?.route) {
        OnboardingScreen.Start.name -> {
            TopAppBar(
                title = {  },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
        OnboardingScreen.Login.name -> {
            TopAppBar(
                title = {  },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
        else -> {
        TopAppBar(
            title = {  },
            navigationIcon = { IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
        }
    }
}

enum class ShowOnboardDialog {
    Default, Login, Signup
}

enum class OnboardingScreen {
    Start, Login, LoginWithPassword, SignupWithPassword,
//    ResetPassword, CodeVerification, CreateNewPassword,

}