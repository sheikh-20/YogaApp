package com.bitvolper.yogazzz.presentation.onboarding.login

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.onboarding.component.LoginComponent
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier,
                onSignInClick: () -> Unit = {  },
                onSignUpClick: () -> Unit = { }) {
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