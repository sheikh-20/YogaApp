package com.bitvolper.yogazzz.presentation.appearance

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.AppLanguagePreference
import com.bitvolper.yogazzz.domain.model.AppThemePreference
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import kotlinx.coroutines.launch

@Composable
fun AppearanceApp(modifier: Modifier = Modifier, accountViewModel: AccountViewModel = hiltViewModel()) {

    var showBottomSheet by remember { mutableStateOf(BottomSheet.Default) }
    val appThemeUIState by accountViewModel.appThemeIndex.collectAsState(initial = AppThemePreference(0))
    val appLanguageUIState by accountViewModel.appLanguageIndex.collectAsState(initial = AppLanguagePreference(0))

    when (showBottomSheet) {
        BottomSheet.Default -> {  }
        BottomSheet.ShowTheme -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    showBottomSheet = BottomSheet.Default
                    accountViewModel.updateAppThemeIndex(it)
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick,
                        selectedAppTheme = appThemeUIState.themeIndex
                    )
                }
            )
        }
        BottomSheet.Language -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    showBottomSheet = BottomSheet.Default
                    accountViewModel.updateAppLanguageIndex(it)
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetLanguageContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick,
                        selectedLanguage = appLanguageUIState.language
                    )
                }
            )
        }
    }

    Scaffold(
        topBar = {
            AppearanceTopAppBar()
        }
    ){ paddingValues ->
        AppearanceScreen(
            paddingValues = paddingValues,
            onThemeClick = {  showBottomSheet = BottomSheet.ShowTheme },
            appThemeIndex = appThemeUIState.themeIndex,
            onLanguageClick = { showBottomSheet = BottomSheet.Language },
            appLanguageIndex = appLanguageUIState.language)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppearanceTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_appearance),
                fontWeight = FontWeight.SemiBold
            )
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
                appThemeIndex: Int = 0,
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
private fun BottomSheetContent(modifier: Modifier = Modifier,
                               onNegativeClick: () -> Unit = { },
                               onPositiveClick: (Int) -> Unit = { _ -> },
                               selectedAppTheme: Int = 0) {

    val theme = listOf(Pair(0, "System Default"), Pair(1, "Light"), Pair(2, "Dark"))

    var currentSelectedAppTheme by remember { mutableStateOf<Int?>(null) }

    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Choose Theme",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold)

        Divider()

        Column {

            theme.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = if (currentSelectedAppTheme != null) currentSelectedAppTheme == it.first else selectedAppTheme == it.first, onClick = { currentSelectedAppTheme = it.first })

                    Text(text = it.second,
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Divider()

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(
                onClick = onNegativeClick,
                modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(onClick = { onPositiveClick(currentSelectedAppTheme ?: 0) }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Ok")
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetLanguageContent(modifier: Modifier = Modifier,
                               onNegativeClick: () -> Unit = { },
                               onPositiveClick: (Int) -> Unit = { _ -> },
                               selectedLanguage: Int = 0) {

    val theme = listOf(Pair(0, "English"), Pair(1, "Spanish"))

    var currentSelectedAppLanguage by remember { mutableStateOf<Int?>(null) }

    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Choose Language",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold)

        Divider()

        Column {

            theme.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = if (currentSelectedAppLanguage != null) currentSelectedAppLanguage == it.first else selectedLanguage == it.first, onClick = { currentSelectedAppLanguage = it.first })

                    Text(text = it.second,
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Divider()

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(
                onClick = onNegativeClick,
                modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(onClick = { onPositiveClick(currentSelectedAppLanguage ?: 0) }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Ok")
            }
        }
    }
}

enum class BottomSheet {
    Default, ShowTheme, Language
}