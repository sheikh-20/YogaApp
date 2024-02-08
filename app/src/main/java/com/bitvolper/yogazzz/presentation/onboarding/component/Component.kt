package com.bitvolper.yogazzz.presentation.onboarding.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R

@Preview(showBackground = true)
@Composable
fun LoginComponent(modifier: Modifier = Modifier, @DrawableRes icon: Int = R.drawable.ic_google, @StringRes text: Int = R.string.continue_with_google, onClick: () -> Unit = {  }) {

    OutlinedButton(onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(50),
        border = BorderStroke(width = .5.dp, color =  MaterialTheme.colorScheme.outlineVariant),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background)
    ) {

        Row(modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            Icon(painter = painterResource(id = icon),
                contentDescription = null,
                modifier = modifier.size(30.dp),
                tint = Color.Unspecified)

            Spacer(modifier = modifier.weight(1f))

            Text(text = stringResource(id = text),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center, 
                color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailComponent(modifier: Modifier = Modifier,
                   email: String = "",
                   onEmailUpdate: (String) -> Unit = {},
                   focusManager: FocusManager = LocalFocusManager.current,
                   emailError: Boolean = false) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailUpdate,
        label = {
            if (!emailError) Text(text = stringResource(R.string.email)) else Text(text = stringResource(R.string.enter_email_address))
        },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Email, contentDescription = null)
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary, unfocusedBorderColor = Color.LightGray),
        isError = emailError
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordComponent(modifier: Modifier = Modifier,
                      password: String = "",
                      onPasswordUpdate: (String) -> Unit = {},
                      focusManager: FocusManager = LocalFocusManager.current,
                      passwordError: Boolean = false) {

    var passwordMask by remember {
        mutableStateOf(true)
    }


    OutlinedTextField(
        value = password,
        onValueChange = onPasswordUpdate,
        label = {
            if (!passwordError) Text(text = stringResource(R.string.password)) else Text(text = stringResource(
                R.string.enter_password
            )
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Lock, contentDescription = null)
        },
        modifier = modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { passwordMask = !passwordMask }) {
                Icon(
                    modifier = modifier.size(24.dp),
                    imageVector = if (passwordMask) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                    contentDescription = null)
            }
        },
        shape = RoundedCornerShape(30),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary, unfocusedBorderColor = Color.LightGray),
        isError = passwordError,
        visualTransformation = if (passwordMask) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun SocialLoginComponent(modifier: Modifier = Modifier, @DrawableRes icon: Int, onClick: () -> Unit) {
    OutlinedIconButton(
        modifier = modifier
            .size(height = 50.dp, width = 70.dp)
            .padding(4.dp),
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(width = .5.dp, color =  Color.LightGray)) {

        Icon(
            modifier = modifier.padding(8.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}