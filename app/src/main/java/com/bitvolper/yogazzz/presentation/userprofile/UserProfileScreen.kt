package com.bitvolper.yogazzz.presentation.userprofile

import android.app.Activity
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.home.account.millisecondToDate
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "UserProfileScreen"
@Composable
fun UserProfileScreen(modifier: Modifier = Modifier,
                      paddingValues: PaddingValues = PaddingValues(),
                      fullName: String = "",
                      onFullNameChange: (String) -> Unit = { _ -> },
                      email: String = "",
                      onEmailChange: (String) -> Unit = { _ -> },
                      gender: Int = 0,

                      onGenderButtonClick: () -> Unit = { },
                      birthdayDate: Long = 31536000000L,

                      onUpdateButtonClick: () -> Unit = { },
                      onCalendarButtonClick: () -> Unit = {  },

                      onProfileClick: (Uri) -> Unit = { _ -> },
                      profileUIState: Resource<Uri> = Resource.Loading,
                      ) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current


    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri ->
            Timber.tag(TAG).d(uri.toString())
            onProfileClick(uri)
        }
    }


    val fullNameInteractionSource = remember { MutableInteractionSource() }
    val emailInteractionSource = remember { MutableInteractionSource() }
    val genderInteractionSource = remember { MutableInteractionSource() }
    val birthdayDateInteractionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding(), bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            IconButton(onClick = { getContent.launch("image/*") }, modifier = modifier.size(80.dp)) {

                when (profileUIState) {
                    is Resource.Loading -> {
                        Icon(painterResource(id = R.drawable.ic_image_placeholder),
                            contentDescription = null,
                            modifier = modifier.size(80.dp).clip(RoundedCornerShape(50)))
                    }

                    is Resource.Failure -> {
                        Icon(imageVector = Icons.Rounded.Spa,
                            contentDescription = null,
                            modifier = modifier.size(80.dp).clip(RoundedCornerShape(50)))
                    }

                    is Resource.Success -> {
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(profileUIState.data)
                                .crossfade(true)
                                .build(),
                            error = painterResource(id = R.drawable.ic_broken_image),
                            placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = modifier.size(80.dp).clip(RoundedCornerShape(50)),
                        )
                    }
                }
            }


            Column {
                Text(
                    text = "Full Name",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = fullName,
                    onValueChange = onFullNameChange,
                    label = {
                        if (fullNameInteractionSource.collectIsFocusedAsState().value.not() && fullName.isEmpty()) {
                            Text(text = "Full name")
                        }
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
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
                    leadingIcon = {
                                  Icon(imageVector = Icons.Rounded.Email, contentDescription = null)
                    },
                    interactionSource = emailInteractionSource,
                    enabled = false
                )
            }

            Column {
                Text(
                    text = "Gender",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = if (gender == 0) "Man" else "Woman",
                    onValueChange = {  },
                    label = {
                        if (genderInteractionSource.collectIsFocusedAsState().value.not() && gender.toString().isEmpty()) {
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
                    trailingIcon = {
                                   IconButton(onClick = onGenderButtonClick) {
                                       Icon(
                                           imageVector = Icons.Rounded.ExpandMore,
                                           contentDescription = null
                                       )
                                   }
                    },
                    interactionSource = genderInteractionSource,
                )
            }

            Column {
                Text(
                    text = "Birthday Date",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = birthdayDate.millisecondToDate(),
                    onValueChange = {  },
                    label = {
                        if (birthdayDateInteractionSource.collectIsFocusedAsState().value.not() && birthdayDate.toString().isEmpty()) {
                            Text(text = "12/12/1995")
                        }
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    interactionSource = birthdayDateInteractionSource,
                    trailingIcon = {
                        IconButton(onClick = onCalendarButtonClick) {
                            Icon(imageVector = Icons.Rounded.CalendarMonth, contentDescription = null)
                        }
                    }
                )
            }
        }


        Spacer(modifier = modifier.weight(1f))

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {


                Button(
                    onClick = {
                        onUpdateButtonClick()
                        (context as Activity).finish()
                    },
                    modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {

                    Text(text = "Update")
                }
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