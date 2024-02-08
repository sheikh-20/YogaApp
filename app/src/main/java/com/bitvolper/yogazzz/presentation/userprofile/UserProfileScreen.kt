package com.bitvolper.yogazzz.presentation.userprofile

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun UserProfileScreen(modifier: Modifier = Modifier,
                      paddingValues: PaddingValues = PaddingValues(),
                      fullName: String = "",
                      onFullNameChange: (String) -> Unit = { _ -> },
                      email: String = "",
                      onEmailChange: (String) -> Unit = { _ -> },
                      gender: String = "",
                      onGenderChange: (String) -> Unit = { _ -> },
                      birthdayDate: String = "",
                      onBirthdayDateChange: (String) -> Unit = { _ -> }
                      ) {


    val focusManager = LocalFocusManager.current

    val fullNameInteractionSource = remember { MutableInteractionSource() }
    val emailInteractionSource = remember { MutableInteractionSource() }
    val genderInteractionSource = remember { MutableInteractionSource() }
    val birthdayDateInteractionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.profile_pic),
                contentDescription = null,
                modifier = modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )


            Column {
                Text(
                    text = "Store Name",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = fullName,
                    onValueChange = onFullNameChange,
                    label = {
                        if (fullNameInteractionSource.collectIsFocusedAsState().value.not() && fullName.isEmpty()) {
                            Text(text = "Store name")
                        }
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    interactionSource = fullNameInteractionSource
                )
            }

            Column {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = {
                        if (emailInteractionSource.collectIsFocusedAsState().value.not() && email.isEmpty()) {
                            Text(text = "Email")
                        }
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    interactionSource = emailInteractionSource
                )
            }

            Column {
                Text(
                    text = "Gender",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = gender,
                    onValueChange = onGenderChange,
                    label = {
                        if (genderInteractionSource.collectIsFocusedAsState().value.not() && gender.isEmpty()) {
                            Text(text = "Gender")
                        }
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    interactionSource = genderInteractionSource
                )
            }

            Column {
                Text(
                    text = "Birthday Date",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = birthdayDate,
                    onValueChange = onBirthdayDateChange,
                    label = {
                        if (birthdayDateInteractionSource.collectIsFocusedAsState().value.not() && birthdayDate.isEmpty()) {
                            Text(text = "12/12/1995")
                        }
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    interactionSource = birthdayDateInteractionSource,
                    trailingIcon = {
                        Icon(imageVector = Icons.Rounded.CalendarMonth, contentDescription = null)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UserProfileLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        UserProfileScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun UserProfileDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        UserProfileScreen()
    }
}