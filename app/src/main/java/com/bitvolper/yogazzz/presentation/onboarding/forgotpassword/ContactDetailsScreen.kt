package com.bitvolper.yogazzz.presentation.onboarding.forgotpassword

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import kotlinx.coroutines.launch

@Composable
fun ContactDetailsScreen(modifier: Modifier = Modifier,
                         email: String = "",
                         onPasswordResetOtp: () -> Unit = { },
                         snackbarHostState: SnackbarHostState = SnackbarHostState(),
                         onComplete: () -> Unit = { }) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {

        Image(
            painter = painterResource(id = R.drawable.ic_reset_password_one),
            contentDescription = null,
            modifier = modifier.size(350.dp),
            contentScale = ContentScale.Crop,
        )

        Text(text = "Select which contact details should we use to reset your password",
            style = MaterialTheme.typography.bodyLarge)

        OutlinedButton(onClick = {  },
            shape = RoundedCornerShape(30),
            border = BorderStroke(width = .5.dp, color =  Color.LightGray),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                IconButton(onClick = { /*TODO*/ },
                    modifier = modifier
                        .background(
                            color = Color(0xFFFF6363),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Email,
                        contentDescription = null,
                        modifier = modifier.size(24.dp),
                        tint = Color.Unspecified)
                }

                Column {
                    Text(text = "Via email",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground)

                    Text(text = email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold)
                }

            }
        }

        Button(onClick = {
            onPasswordResetOtp()
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message = "Password reset email sent successfully", duration = SnackbarDuration.Short)
                onComplete()
            }
                         },
            modifier = modifier
                .shadow(
                    elevation = 4.dp,
                    ambientColor = MaterialTheme.colorScheme.outlineVariant,
                    spotColor = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(50)
                )
                .fillMaxWidth()
                .requiredHeight(50.dp)) {

            Text(text = "Continue",
                modifier = modifier.padding(4.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ContactDetailsLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        ContactDetailsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ContactDetailsDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        ContactDetailsScreen()
    }
}