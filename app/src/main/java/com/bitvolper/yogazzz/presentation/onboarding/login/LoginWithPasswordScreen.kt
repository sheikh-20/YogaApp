package com.bitvolper.yogazzz.presentation.onboarding.login

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.bitvolper.yogazzz.di.model.Login
import com.bitvolper.yogazzz.presentation.onboarding.component.EmailComponent
import com.bitvolper.yogazzz.presentation.onboarding.component.PasswordComponent
import com.bitvolper.yogazzz.presentation.onboarding.component.SocialLoginComponent
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "LoginWithPasswordScreen"
@Composable
fun LoginWithPasswordScreen(modifier: Modifier = Modifier,
                            onResetPasswordClick: () -> Unit = { },
                            onLoginClick: (String, String) -> Unit = { _, _ ->  },
                            loginUIState: SharedFlow<Resource<Login>>? = null,
                            snackbarHostState: SnackbarHostState = SnackbarHostState()
) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        loginUIState?.collectLatest {
            when (it) {
                is Resource.Loading -> {
                    isLoading = true
                }
                is Resource.Failure -> {
                    isLoading = false
                    Timber.tag(TAG).e(it.throwable)
                    snackbarHostState.showSnackbar(message = it.throwable.message.toString(), duration = SnackbarDuration.Long)
                }
                is Resource.Success -> {
                    isLoading = false
                    Timber.tag(TAG).d("Logged in successfully")
//                    PersonalDetailsActivity.startActivity(context as Activity)
                }
            }
        }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(top = 16.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {

        Text(
            text = "Welcome back",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Text(
            text = "Please enter your email and password to sign in.",
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
                    focusManager = focusManager)
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Password")

                PasswordComponent(
                    password = password,
                    onPasswordUpdate = { password = it },
                    focusManager = focusManager
                )
            }

            Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                Checkbox(checked = false, onCheckedChange = {})
                Text(text = "Remember me", style = MaterialTheme.typography.labelLarge)
            }
        }

        Divider(modifier = modifier.fillMaxWidth())

        TextButton(onClick = onResetPasswordClick) {
            Text(text = "Forgot the password?", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account?")
            TextButton(onClick = {    }) {
                Text(text = "Sign up", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Divider(modifier = modifier.weight(1f), color = Color.LightGray)
            Text(text = "or continue with", style = MaterialTheme.typography.titleMedium)
            Divider(modifier = modifier.weight(1f), color = Color.LightGray)
        }

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SocialLoginComponent(
                icon = R.drawable.ic_facebook,
                onClick = {   })

            SocialLoginComponent(
                icon = R.drawable.ic_google,
                onClick = {   })

            SocialLoginComponent(
                icon = R.drawable.ic_github,
                onClick = {   })
        }

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Button(onClick = { onLoginClick(email, password)  },
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
                colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color.Red)) {


                if (isLoading) {
                    CircularProgressIndicator(modifier = modifier.size(30.dp), strokeWidth = 2.dp, trackColor = Color.White)
                } else {
                    Text(text = stringResource(id = R.string.signin), color = colorResource(id = R.color.white), modifier = modifier.padding(4.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginWithPasswordLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        LoginWithPasswordScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LoginWithPasswordDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        LoginWithPasswordScreen()
    }
}