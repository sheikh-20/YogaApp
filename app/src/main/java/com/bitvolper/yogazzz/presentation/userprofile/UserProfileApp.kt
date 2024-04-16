package com.bitvolper.yogazzz.presentation.userprofile

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileApp(modifier: Modifier = Modifier, accountViewModel: AccountViewModel = hiltViewModel()) {

    val accountInfoUIState by accountViewModel.accountInfoUIState.collectAsState()
    val profileUIState by accountViewModel.profilePhotoUIState.collectAsState()

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    val state = rememberDatePickerState()
    var openDialog by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        BottomSheet(
            onDismiss = { showBottomSheet = false },
            onNegativeClick = { showBottomSheet = false },
            onPositiveClick = {
                accountViewModel.updateGender(it)
                showBottomSheet = false
            },
            contentSheet = {
                    onNegativeClick, onPositiveClick ->

                BottomSheetContent(
                    onNegativeClick = onNegativeClick,
                    onPositiveClick = onPositiveClick
                )
            }
        )
    }

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        accountViewModel.updateBirthdayDate(state.selectedDateMillis ?: return@TextButton)
                        openDialog = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text(stringResource(R.string.cancel_text))
                }
            }
        ) {
            DatePicker(
                state = state
            )
        }
    }


    Scaffold(
        topBar = {
            UserProfileTopAppBar()
        }
    ) { paddingValues ->
        UserProfileScreen(
            paddingValues = paddingValues,
            fullName = accountInfoUIState.fullName ?: "",
            onFullNameChange = accountViewModel::updateFullName,
            onUpdateButtonClick = accountViewModel::updateUserProfile,
            email = accountInfoUIState.email ?: "",
            gender = accountInfoUIState.gender ?: 0,
            onGenderButtonClick = { showBottomSheet = true },
            onCalendarButtonClick = { openDialog = true },
            birthdayDate = accountInfoUIState.birthdayDate ?: 0L,

            onProfileClick = accountViewModel::uploadProfilePhoto,
            profileUIState = profileUIState,)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserProfileTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.my_profile),
                fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        actions = {

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(modifier: Modifier = Modifier,
                onDismiss: () -> Unit = {},
                onNegativeClick: () -> Unit = {},
                onPositiveClick: (Int) -> Unit = {},
                contentSheet: @Composable (onNegativeClick: () -> Unit, onPositiveClick: (Int) -> Unit) -> Unit = { _, _ -> }
) {

    val bottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            coroutineScope.launch {
                onDismiss()
                bottomSheet.hide()
            }
        },
        sheetState = bottomSheet,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {

        contentSheet(onNegativeClick, onPositiveClick)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetContent(modifier: Modifier = Modifier, onNegativeClick: () -> Unit = { }, onPositiveClick: (Int) -> Unit = {}) {
    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = stringResource(R.string.select_gender),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        Column(modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {

            Text(text = stringResource(id = R.string.man),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onPositiveClick(0) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ))

            Text(text = stringResource(id = R.string.woman),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onPositiveClick(1) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            )
        }
    }
}