package com.bitvolper.yogazzz.presentation.onboarding.signup

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.Register
import com.bitvolper.yogazzz.domain.usecase.SignInGoogleInteractor
import com.bitvolper.yogazzz.presentation.accountsetup.AccountSetupActivity
import com.bitvolper.yogazzz.presentation.home.HomeActivity
import com.bitvolper.yogazzz.presentation.onboarding.ShowOnboardDialog
import com.bitvolper.yogazzz.presentation.onboarding.component.EmailComponent
import com.bitvolper.yogazzz.presentation.onboarding.component.LoginComponent
import com.bitvolper.yogazzz.presentation.onboarding.component.PasswordComponent
import com.bitvolper.yogazzz.presentation.onboarding.component.SocialLoginComponent
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.presentation.viewmodel.OnboardUIState
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "SignupWithPasswordScreen"
@Composable
fun SignupWithPasswordScreen(modifier: Modifier = Modifier,
                             paddingValues: PaddingValues = PaddingValues(),
                             onSignupClick: (String, String) -> Unit = { _, _ ->  },
                             snackbarHostState: SnackbarHostState = SnackbarHostState(),
                             onGoogleSignInClick: (Activity?, Intent?) -> Unit = { _, _ ->},
                             onSocialSignIn: SharedFlow<Resource<AuthResult>>? = null,
                             signupUIState: OnboardUIState = OnboardUIState(),
                             showDialog: (ShowOnboardDialog) -> Unit = { _ -> },
                             onUpdateUserProfile: () -> Unit = { }
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    var isLoading by remember { mutableStateOf(false) }

    var isReadCondition by remember { mutableStateOf(false) }

    if (isLoading) {
        showDialog(ShowOnboardDialog.Signup)
    } else {
        showDialog(ShowOnboardDialog.Default)
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                    isLoading = true
                }
                is Resource.Failure -> {
                    isLoading = false

                    if (it.throwable is FirebaseAuthUserCollisionException) {
                        snackbarHostState.showSnackbar(message = "Already created, Try Login!")
                        Timber.tag("Login").e(it.throwable)
                    } else {
                        snackbarHostState.showSnackbar(message = "Failure!")
                        Timber.tag("Login").e(it.throwable)
                    }
                }
                is Resource.Success -> {
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

    Box(modifier = modifier
        .fillMaxSize()
        .padding(bottom = 16.dp)) {

        Column(modifier = modifier
            .padding(top = paddingValues.calculateTopPadding(), bottom = 100.dp)
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Text(
                text = "Join YogazzZ today \uD83D\uDC64",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = "Start your personalized wellness experiences.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Column(modifier = modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Email")

                    EmailComponent(
                        email = email,
                        onEmailUpdate = { email = it },
                        focusManager = focusManager,
                        emailError = signupUIState.isEmailError)
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Password")

                    PasswordComponent(
                        password = password,
                        onPasswordUpdate = { password = it },
                        focusManager = focusManager,
                        passwordError = signupUIState.isPasswordError
                    )
                }

                Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                    Checkbox(checked = isReadCondition, onCheckedChange = { isReadCondition = true })
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "I agree to YogazzZ Terms & Conditions ", style = MaterialTheme.typography.labelLarge)
                    }
                }

                Row(modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Already have an account?")
                    TextButton(onClick = {  }) {
                        Text(text = "Sign In", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            Row(modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Divider(modifier = modifier.weight(1f), color = Color.LightGray)
                Text(text = "or")
                Divider(modifier = modifier.weight(1f), color = Color.LightGray)
            }

            Column(modifier = modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
        }

        Column(modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomCenter), verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Button(onClick = { onSignupClick(email, password) },
                modifier = modifier
                    .shadow(
                        elevation = 4.dp,
                        ambientColor = MaterialTheme.colorScheme.outlineVariant,
                        spotColor = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(50)
                    )
                    .fillMaxWidth()
                    .requiredHeight(50.dp)
                    .padding(horizontal = 16.dp),
                enabled = isReadCondition) {

                if (isLoading) {
                    CircularProgressIndicator(modifier = modifier.size(30.dp), strokeWidth = 2.dp, trackColor = Color.White)
                } else {
                    Text(text = stringResource(id = R.string.sign_up), modifier = modifier.padding(4.dp))
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignupWithPasswordLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SignupWithPasswordScreen()
    }
}


@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SignupWithPasswordDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SignupWithPasswordScreen()
    }
}