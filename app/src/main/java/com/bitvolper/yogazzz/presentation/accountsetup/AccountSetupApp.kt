package com.bitvolper.yogazzz.presentation.accountsetup

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AccountSetupApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {  }
    ) { paddingValues ->
        SelectGenderScreen(paddingValues = paddingValues)
    }
}