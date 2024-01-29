package com.bitvolper.yogazzz.presentation.onboarding.reset

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.onboarding.component.EmailComponent
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun PasswordResetScreen(modifier: Modifier = Modifier, onContinueClick: () -> Unit = { }) {
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)) {

            Text(
                text = "Reset your password",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = modifier.fillMaxWidth()
            )

            Text(
                text = "Please enter your email and we will send an OTP code in the next step to reset your password",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = modifier.fillMaxWidth()
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)){

                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold
                )

                EmailComponent(
                    email = email,
                    onEmailUpdate = { email = it },
                    focusManager = focusManager)
            }

        }

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Button(onClick = onContinueClick,
                modifier = modifier
                    .shadow(
                        elevation = 4.dp,
                        ambientColor = MaterialTheme.colorScheme.outlineVariant,
                        spotColor = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(50)
                    )
                    .fillMaxWidth().requiredHeight(50.dp),
                colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color.Red)) {

                Text(text = stringResource(R.string.continues), color = colorResource(id = R.color.white), modifier = modifier.padding(4.dp))

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PasswordResetLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        PasswordResetScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PasswordResetDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        PasswordResetScreen()
    }
}