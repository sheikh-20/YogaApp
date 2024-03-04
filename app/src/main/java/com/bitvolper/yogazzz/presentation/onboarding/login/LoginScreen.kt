package com.bitvolper.yogazzz.presentation.onboarding.login

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.usecase.SignInGoogleInteractor
import com.bitvolper.yogazzz.presentation.accountsetup.AccountSetupActivity
import com.bitvolper.yogazzz.presentation.home.HomeActivity
import com.bitvolper.yogazzz.presentation.onboarding.ShowOnboardDialog
import com.bitvolper.yogazzz.presentation.onboarding.component.LoginComponent
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier,
                onSignInClick: () -> Unit = {  },
                onSignUpClick: () -> Unit = { },
                onGoogleSignInClick: (Activity?, Intent?) -> Unit = { _, _ ->},
                onSocialSignIn: SharedFlow<Resource<AuthResult>>? = null,
                snackbarHostState: SnackbarHostState = SnackbarHostState(),
                showDialog: (ShowOnboardDialog) -> Unit = {  _ -> },
                onUpdateUserProfile: () -> Unit = { }
) {


    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Timber.tag("LoginScreen").d("OK -> ${result.data.toString()}")

                onGoogleSignInClick(context as Activity, result.data)
            } else {
                Timber.tag("LoginScreen").e("ERROR")
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        onSocialSignIn?.collectLatest {
            when(it) {
                is Resource.Loading -> {
                    showDialog(ShowOnboardDialog.Login)
                    isLoading = true
                }
                is Resource.Failure -> {
                    showDialog(ShowOnboardDialog.Default)
                    isLoading = false
                    if (it.throwable is FirebaseAuthInvalidUserException) {
                        snackbarHostState.showSnackbar(message = "Email does not exists, Try signup!")
                    } else {
                        snackbarHostState.showSnackbar(message = "Failure!")
                        Timber.tag("Login").e(it.throwable)
                    }
                }
                is Resource.Success -> {
                    showDialog(ShowOnboardDialog.Default)
                    isLoading = false
                    Timber.tag("Login").d("Google Success")

                    if (it.data.additionalUserInfo?.isNewUser == true) {
                        onUpdateUserProfile()
                        (context as Activity).finish()
                        AccountSetupActivity.startActivity(context as Activity)
                    } else {
                        (context as Activity).finish()
                        HomeActivity.startActivity((context as Activity))
                    }
                }
            }
        }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = R.drawable.ic_yoga),
            contentDescription = null,
            modifier = modifier.size(200.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.Crop)

       Column(verticalArrangement = Arrangement.spacedBy(16.dp),
           horizontalAlignment = Alignment.CenterHorizontally) {
           Text(text = "Let's get started",
               style = MaterialTheme.typography.headlineLarge,
               fontWeight = FontWeight.SemiBold,
               textAlign = TextAlign.Center)

           Text(text = "Let's dive into your account!",
               style = MaterialTheme.typography.bodyLarge,
               textAlign = TextAlign.Center)
       }

        Spacer(modifier = modifier.weight(1f))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            LoginComponent(icon = R.drawable.ic_google, text = R.string.continue_with_google) {
                coroutineScope.launch {
                    val result = SignInGoogleInteractor.signIn(context)
                    launcher.launch(IntentSenderRequest.Builder(result ?: return@launch).build())
                }
            }
            LoginComponent(icon = R.drawable.ic_facebook, text = R.string.continue_with_facebook) {

            }
            LoginComponent(icon = R.drawable.ic_github, text = R.string.continue_with_github) {

            }
        }

        Spacer(modifier = modifier.weight(1f))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {


            Button(onClick = onSignUpClick, modifier = modifier
                .fillMaxWidth()
                .requiredHeight(50.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)) {
                Text(text = "Sign Up", fontWeight = FontWeight.SemiBold)
            }

            Button(onClick = onSignInClick, modifier = modifier
                .fillMaxWidth()
                .requiredHeight(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)
            ) {
                Text(text = "Log In", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = modifier.weight(1f))

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Privacy Policy", style = MaterialTheme.typography.bodySmall)

            HorizontalPagerIndicator(pagerState = rememberPagerState(), indicatorWidth = 5.dp, inactiveColor = MaterialTheme.colorScheme.onBackground, activeColor = MaterialTheme.colorScheme.onBackground)

            Text(text = "Terms & Services", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        LoginScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LoginScreenDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        LoginScreen()
    }
}