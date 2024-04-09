package com.bitvolper.yogazzz.presentation.accountsecurity

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.home.HomeActivity
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import kotlinx.coroutines.launch

@Composable
fun AccountSecurityApp(modifier: Modifier = Modifier, accountViewModel: AccountViewModel = hiltViewModel()) {


    var showBottomSheet by remember { mutableStateOf(BottomSheet.Default) }

    when (showBottomSheet) {
        BottomSheet.Default -> {}
        BottomSheet.Deactivate -> {
            BottomSheet(
                onDismiss = {
                    showBottomSheet = BottomSheet.Default
                },
                onNegativeClick = {
                    showBottomSheet = BottomSheet.Default
                },
                onPositiveClick = {
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = { onNegativeClick, onPositiveClick ->

                    BottomSheetDeactivateContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick
                    )
                })
        }
        BottomSheet.DeleteAccount -> {
            BottomSheet(
                onDismiss = {
                    showBottomSheet = BottomSheet.Default
                },
                onNegativeClick = {
                    showBottomSheet = BottomSheet.Default
                },
                onPositiveClick = {
                    accountViewModel.onDeleteAccount()
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = { onNegativeClick, onPositiveClick ->
                    BottomSheetDeleteAccountContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick
                    )
                })
        }
    }

    Scaffold(
        topBar = {
            AccountSecurityTopAppBar()
        }
    ) { paddingValues ->
        AccountSecurityScreen(
            paddingValues = paddingValues,
            onDeactivateClick = { showBottomSheet = BottomSheet.Deactivate },
            onDeleteAccountClick = { showBottomSheet = BottomSheet.DeleteAccount })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountSecurityTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.account_security),
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
private fun BottomSheet(modifier: Modifier = Modifier,
                        onDismiss: () -> Unit = {},
                        onNegativeClick: () -> Unit = {},
                        onPositiveClick: () -> Unit = {},
                        contentSheet: @Composable (onNegativeClick: () -> Unit, onPositiveClick: () -> Unit) -> Unit = { _, _ -> }
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
private fun BottomSheetDeactivateContent(modifier: Modifier = Modifier, onNegativeClick: () -> Unit = { }, onPositiveClick: () -> Unit = {}) {
    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Deactivate Account",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        Text(text = "Are you sure you want to deactivate account?",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth())

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(onClick = onPositiveClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Yes, Deactivate")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetDeleteAccountContent(modifier: Modifier = Modifier, onNegativeClick: () -> Unit = { }, onPositiveClick: () -> Unit = {}) {
    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Delete Account",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        Text(text = "Are you sure you want to delete account?",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth())

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(onClick = onPositiveClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Yes, Delete")
            }
        }
    }
}


enum class BottomSheet {
    Default, Deactivate, DeleteAccount
}