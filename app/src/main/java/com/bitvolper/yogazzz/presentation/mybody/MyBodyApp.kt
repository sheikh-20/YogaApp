package com.bitvolper.yogazzz.presentation.mybody

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBodyApp(modifier: Modifier = Modifier, accountViewModel: AccountViewModel = hiltViewModel()) {

    val accountInfoUIState by accountViewModel.accountInfoUIState.collectAsState()

    var showBottomSheet by remember {
        mutableStateOf<BottomSheet>(BottomSheet.Default)
    }

    val state = rememberDatePickerState()
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        accountViewModel.updateBirthdayDate(state.selectedDateMillis ?: return@TextButton)
                        accountViewModel.updateUserProfile()
                        openDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(
                state = state
            )
        }
    }



    when (showBottomSheet) {
        BottomSheet.Default -> {  }
        BottomSheet.Height -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default   },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    accountViewModel.updateHeight(it.toString().toInt())
                    accountViewModel.updateUserProfile()
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetHeightContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick,
                        height = accountViewModel.height,
                        onHeight = accountViewModel::onHeight
                    )
                }
            )
        }
        BottomSheet.HeightFt -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default   },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    accountViewModel.updateHeightFt(it.toString().toDouble())
                    accountViewModel.updateUserProfile()
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetHeightFtContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick,
                        heightInFt = accountViewModel.heightInFt,
                        onHeightFt = accountViewModel::onHeightFt
                    )
                }
            )
        }
        BottomSheet.CurrentWeight -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default   },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    accountViewModel.updateCurrentWeight(it.toString().toDouble())
                    accountViewModel.updateUserProfile()
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetCurrentWeightContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick,
                        currentWeight = accountViewModel.currentWeight,
                        onCurrentWeight = accountViewModel::onCurrentWeight
                    )
                }
            )
        }
        BottomSheet.CurrentWeightInLb -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default   },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    accountViewModel.updateCurrentWeightLb(it.toString().toDouble())
                    accountViewModel.updateUserProfile()
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetCurrentWeightLbContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick,
                        currentWeightInLb = accountViewModel.currentWeightInLb,
                        onCurrentWeightLb = accountViewModel::onCurrentWeightLb
                    )
                }
            )
        }
        BottomSheet.TargetWeight -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default   },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    accountViewModel.updateTargetWeight(it.toString().toDouble())
                    accountViewModel.updateUserProfile()
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetTargetWeightContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick,
                        targetWeight = accountViewModel.targetWeight,
                        onTargetWeight = accountViewModel::onTargetWeight
                    )
                }
            )
        }
        BottomSheet.TargetWeightInLb -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default   },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    accountViewModel.updateTargetWeightLb(it.toString().toDouble())
                    accountViewModel.updateUserProfile()
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetTargetWeightLbContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick,
                        targetWeightInLb = accountViewModel.targetWeightInLb,
                        onTargetWeightLb = accountViewModel::onTargetWeightLb
                    )
                }
            )
        }
        BottomSheet.Gender -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    accountViewModel.updateGender(it.toString().toInt())
                    accountViewModel.updateUserProfile()
                    showBottomSheet = BottomSheet.Default
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetGenderContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick
                    )
                }
            )
        }
    }

    Scaffold(
        topBar = {
            MyBodyTopAppBar()
        }
    ) { paddingValues ->
        MyBodyScreen(
            paddingValues = paddingValues,
            height = accountInfoUIState.height ?: 0,
            onHeightButtonClick = {
                accountViewModel.onHeight(accountInfoUIState.height ?: 0)
                showBottomSheet = BottomSheet.Height },

            heightFt = accountInfoUIState.heightInFt ?: 0.0,
            onHeightFtButtonClick = {
                accountViewModel.onHeightFt(accountInfoUIState.heightInFt ?: 0.0)
                showBottomSheet = BottomSheet.HeightFt
            },

            currentWeight = accountInfoUIState.currentWeight ?: 0.0,
            onCurrentWeightButtonClick = {
                accountViewModel.onCurrentWeight(accountInfoUIState.currentWeight ?: 0.0)
                showBottomSheet = BottomSheet.CurrentWeight },

            currentWeightInLb = accountInfoUIState.currentWeightInLb ?: 0.0,
            onCurrentWeightLbButtonClick = {
                accountViewModel.onCurrentWeightLb(accountInfoUIState.currentWeightInLb ?: 0.0)
                showBottomSheet = BottomSheet.CurrentWeightInLb
            },

            targetWeight = accountInfoUIState.targetWeight ?: 0.0,
            onTargetWeightButtonClick = {
                accountViewModel.onTargetWeight(accountInfoUIState.targetWeight ?: 0.0)
                showBottomSheet = BottomSheet.TargetWeight },

            targetWeightInLb = accountInfoUIState.targetWeightInLb ?: 0.0,
            onTargetWeightLbButtonClick = {
                accountViewModel.onTargetWeightLb(accountInfoUIState.targetWeightInLb ?: 0.0)
                showBottomSheet = BottomSheet.TargetWeightInLb },


            age = accountInfoUIState.birthdayDate ?: 0L,
            onAgeButtonClick = { openDialog = true },
            gender = accountInfoUIState.gender ?: 0,
            onGenderButtonClick = { showBottomSheet = BottomSheet.Gender }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyBodyTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "My Body",
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

enum class BottomSheet {
    Default, Height, HeightFt, CurrentWeight, CurrentWeightInLb, TargetWeight, TargetWeightInLb, Gender
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheet(modifier: Modifier = Modifier,
                        onDismiss: () -> Unit = {},
                        onNegativeClick: () -> Unit = {},
                        onPositiveClick: (Any) -> Unit = {},
                        contentSheet: @Composable (onNegativeClick: () -> Unit, onPositiveClick: (Any) -> Unit) -> Unit = { _, _ -> }
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


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetHeightContent(modifier: Modifier = Modifier,
                                     onNegativeClick: () -> Unit = { },
                                     onPositiveClick: (Int) -> Unit = { _ -> },
                                     height: Int = 0,
                                     onHeight: (Int) -> Unit = { _ -> }) {

    val focusManager = LocalFocusManager.current
    val color = MaterialTheme.colorScheme.primary

    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Enter Height",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        OutlinedTextField(
            value = "$height",
            onValueChange = { onHeight(it.toIntOrNull() ?: 0) },
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                unfocusedBorderColor = color,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.primary
            )
        )

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Button(onClick = { onPositiveClick(height) }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Ok")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetHeightFtContent(modifier: Modifier = Modifier,
                                     onNegativeClick: () -> Unit = { },
                                     onPositiveClick: (Double) -> Unit = { _ -> },
                                     heightInFt: Double = 0.0,
                                     onHeightFt: (Double) -> Unit = { _ -> }) {

    val focusManager = LocalFocusManager.current
    val color = MaterialTheme.colorScheme.primary

    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Enter Height",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        OutlinedTextField(
            value = "$heightInFt",
            onValueChange = { onHeightFt(it.toDoubleOrNull() ?: 0.0) },
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                unfocusedBorderColor = color,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.primary
            )
        )

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Button(onClick = { onPositiveClick(heightInFt) }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Ok")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetCurrentWeightContent(modifier: Modifier = Modifier,
                                            onNegativeClick: () -> Unit = { },
                                            onPositiveClick: (Double) -> Unit = { _ -> },
                                            currentWeight: Double = 0.0,
                                            onCurrentWeight: (Double) -> Unit = { _ -> }) {

    val focusManager = LocalFocusManager.current
    val color = MaterialTheme.colorScheme.primary

    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Enter Weight",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        OutlinedTextField(
            value = "$currentWeight",
            onValueChange = { onCurrentWeight(it.toDoubleOrNull() ?: 0.0) },
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                unfocusedBorderColor = color,
                textColor = MaterialTheme.colorScheme.primary
            )
        )

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Button(onClick = { onPositiveClick(currentWeight) }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Ok")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetCurrentWeightLbContent(modifier: Modifier = Modifier,
                                            onNegativeClick: () -> Unit = { },
                                            onPositiveClick: (Double) -> Unit = { _ -> },
                                            currentWeightInLb: Double = 0.0,
                                            onCurrentWeightLb: (Double) -> Unit = { _ -> }) {

    val focusManager = LocalFocusManager.current
    val color = MaterialTheme.colorScheme.primary

    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Enter Weight",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        OutlinedTextField(
            value = "$currentWeightInLb",
            onValueChange = { onCurrentWeightLb(it.toDoubleOrNull() ?: 0.0) },
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                unfocusedBorderColor = color,
                textColor = MaterialTheme.colorScheme.primary
            )
        )

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Button(onClick = { onPositiveClick(currentWeightInLb) }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Ok")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetTargetWeightContent(modifier: Modifier = Modifier,
                                           onNegativeClick: () -> Unit = { },
                                           onPositiveClick: (Double) -> Unit = { _ -> },
                                           targetWeight: Double = 0.0,
                                           onTargetWeight: (Double) -> Unit = { _ -> }) {

    val focusManager = LocalFocusManager.current
    val color = MaterialTheme.colorScheme.primary

    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Enter Weight",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        OutlinedTextField(
            value = "$targetWeight",
            onValueChange = { onTargetWeight(it.toDoubleOrNull() ?: 0.0) },
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                unfocusedBorderColor = color,
                textColor = MaterialTheme.colorScheme.primary
            )
        )

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Button(onClick = { onPositiveClick(targetWeight) }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Ok")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetTargetWeightLbContent(modifier: Modifier = Modifier,
                                             onNegativeClick: () -> Unit = { },
                                             onPositiveClick: (Double) -> Unit = { _ -> },
                                             targetWeightInLb: Double = 0.0,
                                             onTargetWeightLb: (Double) -> Unit = { _ -> }
                                             ) {

    val focusManager = LocalFocusManager.current
    val color = MaterialTheme.colorScheme.primary


    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Enter Weight",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        OutlinedTextField(
            value = "$targetWeightInLb",
            onValueChange = { onTargetWeightLb(it.toDoubleOrNull() ?: 0.0) },
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                unfocusedBorderColor = color,
                textColor = MaterialTheme.colorScheme.primary
            )
        )

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Button(onClick = { onPositiveClick(targetWeightInLb) }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Ok")
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetGenderContent(modifier: Modifier = Modifier, onNegativeClick: () -> Unit = { }, onPositiveClick: (Int) -> Unit = {}) {
    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Select Gender",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        androidx.compose.material3.Divider()

        Column(modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {

            Text(text = "Man",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onPositiveClick(0) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null))

            Text(text = "Woman",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
                    .clickable(
                        onClick = { onPositiveClick(1) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null)
            )
        }
    }
}